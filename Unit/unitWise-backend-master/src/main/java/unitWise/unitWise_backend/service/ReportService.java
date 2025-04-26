package unitWise.unitWise_backend.service;

import org.springframework.stereotype.Service;
import unitWise.unitWise_backend.dto.article.ArticleResponse;
import unitWise.unitWise_backend.dto.businessUnit.BusinessUnitResponse;
import unitWise.unitWise_backend.dto.report.CashFlowByProjectResponseDto;
import unitWise.unitWise_backend.entity.Payment;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService {

    private static final BigDecimal INITIAL_VALUE = BigDecimal.valueOf(0.0);
    private static final int VERSION_TWO = 2;
    private final Set<Long> processedPaymentIds = new HashSet<>();

    private final ArticleService articleService;
    private final BusinessUnitService businessUnitService;
    private final PaymentService paymentService;
    private final CurrencyConverter currencyConverter;

    public ReportService(ArticleService articleService,
                         BusinessUnitService businessUnitService,
                         PaymentService paymentService,
                         CurrencyConverter currencyConverter) {
        this.articleService = articleService;
        this.businessUnitService = businessUnitService;
        this.paymentService = paymentService;
        this.currencyConverter = currencyConverter;
    }

    public CashFlowByProjectResponseDto getReport(Long projectId, int year, List<Long> unitIds, Long accountId) {
        List<Payment> payments = paymentService.getPaymentsByProjectIdAndYear(projectId, year, unitIds, accountId);
        payments = currencyConverter.convertPaymentsToKZT(payments);
        return mapPaymentsToCashFlowResponse(payments, projectId, year);
    }


    public CashFlowByProjectResponseDto mapPaymentsToCashFlowResponse(List<Payment> payments, Long projectId, int year) {
        CashFlowByProjectResponseDto response = new CashFlowByProjectResponseDto();
        response.setProjectId(projectId);
        response.setYear(year);

        // Maps to store hierarchical data
        Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> categoryMap = new HashMap<>();
        Map<String, Map<String, CashFlowByProjectResponseDto.CashFlowDataDto>> categoryArticleMap = new HashMap<>();
        Map<String, Map<String, Map<String, CashFlowByProjectResponseDto.CashFlowDataDto>>> categoryArticleBusinessUnitMap = new HashMap<>();

        processedPaymentIds.clear();

        // Process all payments
        for (Payment payment : payments) {
            if (payment.getDateAccrual() == null) continue;

//            String month = new SimpleDateFormat("MMM").format(payment.getDateTransfer()).toLowerCase();

            String month = (payment.getDateAccrual() != null) ?
                    payment.getDateAccrual().format(DateTimeFormatter.ofPattern("MMM")).toLowerCase() : "unknown";


            // Get necessary data
            ArticleResponse article = articleService.getArticleById(payment.getArticleId());
            BusinessUnitResponse businessUnit = businessUnitService.getBusinessUnitById(payment.getCompanyId());

            String categoryName = article.getCategory();
            String articleName = article.getItem();
            String businessUnitName = businessUnit.getName();

            // Create or get category-level data
            categoryMap.putIfAbsent(categoryName, createEmptyCashFlowData(categoryName));

            // Initialize the maps for this category if they don't exist
            categoryArticleMap.putIfAbsent(categoryName, new HashMap<>());
            categoryArticleBusinessUnitMap.putIfAbsent(categoryName, new HashMap<>());

            // Create or get article-level data
            Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> articleMap = categoryArticleMap.get(categoryName);
            articleMap.putIfAbsent(articleName, createEmptyCashFlowData(articleName));

            // Initialize the business unit map for this article if it doesn't exist
            categoryArticleBusinessUnitMap.get(categoryName).putIfAbsent(articleName, new HashMap<>());

            // Create or get business-unit-level data
            Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> businessUnitMap =
                    categoryArticleBusinessUnitMap.get(categoryName).get(articleName);
            businessUnitMap.putIfAbsent(businessUnitName, createEmptyCashFlowData(businessUnitName));

            // Get the data DTOs for each level
            CashFlowByProjectResponseDto.CashFlowDataDto categoryDto = categoryMap.get(categoryName);
            CashFlowByProjectResponseDto.CashFlowDataDto articleDto = articleMap.get(articleName);
            CashFlowByProjectResponseDto.CashFlowDataDto businessUnitDto = businessUnitMap.get(businessUnitName);

            // Create the plan/fact difference DTO for this payment
            CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto planFactDto = getPlanFactDifferenceDto(payment);

            // Update all three levels with the payment data
            updateMonthData(categoryDto, month, planFactDto);
            updateMonthData(articleDto, month, planFactDto);
            updateMonthData(businessUnitDto, month, planFactDto);
        }

        // Build the final hierarchical structure
        List<CashFlowByProjectResponseDto.CashFlowDataDto> resultData = new ArrayList<>();

        // For each category
        for (String categoryName : categoryMap.keySet()) {
            CashFlowByProjectResponseDto.CashFlowDataDto categoryDto = categoryMap.get(categoryName);
            List<CashFlowByProjectResponseDto.CashFlowDataDto> articleDtos = new ArrayList<>();

            // For each article in this category
            Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> articleMap = categoryArticleMap.get(categoryName);
            for (String articleName : articleMap.keySet()) {
                CashFlowByProjectResponseDto.CashFlowDataDto articleDto = articleMap.get(articleName);
                List<CashFlowByProjectResponseDto.CashFlowDataDto> businessUnitDtos = new ArrayList<>();

                // For each business unit in this article
                Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> businessUnitMap =
                        categoryArticleBusinessUnitMap.get(categoryName).get(articleName);

                if (businessUnitMap != null) {
                    businessUnitDtos.addAll(businessUnitMap.values());
                }

                // Add business units as children of the article
                articleDto.setChildren(businessUnitDtos);
                articleDtos.add(articleDto);
            }

            // Add articles as children of the category
            categoryDto.setChildren(articleDtos);
            resultData.add(categoryDto);
        }

        // Set the final data in the response
        response.setData(resultData);

        // Calculate totals for all levels
        calculateTotals(response.getData());

        return response;
    }

    // Helper method to create an empty CashFlowDataDto
    private CashFlowByProjectResponseDto.CashFlowDataDto createEmptyCashFlowData(String name) {
        return new CashFlowByProjectResponseDto.CashFlowDataDto(
                name,
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // jan
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // feb
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // mar
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // apr
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // may
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // jun
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // jul
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // aug
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // sep
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // oct
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // nov
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // dec
                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"), // total
                false, // expanded
                new ArrayList<>() // children
        );
    }

    // Helper method to update month data in a CashFlowDataDto
    private void updateMonthData(CashFlowByProjectResponseDto.CashFlowDataDto dataDto, String month,
                                 CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto planFactDto) {
        switch (month) {
            case "jan": dataDto.setJan(addValues(dataDto.getJan(), planFactDto)); break;
            case "feb": dataDto.setFeb(addValues(dataDto.getFeb(), planFactDto)); break;
            case "mar": dataDto.setMar(addValues(dataDto.getMar(), planFactDto)); break;
            case "apr": dataDto.setApr(addValues(dataDto.getApr(), planFactDto)); break;
            case "may": dataDto.setMay(addValues(dataDto.getMay(), planFactDto)); break;
            case "jun": dataDto.setJun(addValues(dataDto.getJun(), planFactDto)); break;
            case "jul": dataDto.setJul(addValues(dataDto.getJul(), planFactDto)); break;
            case "aug": dataDto.setAug(addValues(dataDto.getAug(), planFactDto)); break;
            case "sep": dataDto.setSep(addValues(dataDto.getSep(), planFactDto)); break;
            case "oct": dataDto.setOct(addValues(dataDto.getOct(), planFactDto)); break;
            case "nov": dataDto.setNov(addValues(dataDto.getNov(), planFactDto)); break;
            case "dec": dataDto.setDec(addValues(dataDto.getDec(), planFactDto)); break;
        }
    }

    // Helper method to add values from two PlanFactDifferenceDto objects
    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto addValues(
            CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto existing,
            CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto toAdd) {

        BigDecimal existingPlan = new BigDecimal(existing.getPlan());
        BigDecimal existingFact = new BigDecimal(existing.getFact());

        BigDecimal toAddPlan = new BigDecimal(toAdd.getPlan());
        BigDecimal toAddFact = new BigDecimal(toAdd.getFact());

        BigDecimal newPlan = existingPlan.add(toAddPlan);
        BigDecimal newFact = existingFact.add(toAddFact);

        return getPlanFactDto(newPlan, newFact);
    }

    // Helper method to calculate totals for all levels
    private void calculateTotals(List<CashFlowByProjectResponseDto.CashFlowDataDto> dataList) {
        for (CashFlowByProjectResponseDto.CashFlowDataDto data : dataList) {
            // Calculate total for this item
            CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto total = calculateTotal(data);
            data.setTotal(total);

            // Recursively calculate totals for children
            if (data.getChildren() != null && !data.getChildren().isEmpty()) {
                calculateTotals(data.getChildren());
            }
        }
    }

    // Helper method to calculate total from monthly data
    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto calculateTotal(
            CashFlowByProjectResponseDto.CashFlowDataDto data) {

        BigDecimal totalPlan = BigDecimal.ZERO;
        BigDecimal totalFact = BigDecimal.ZERO;

        // Add up all months
        totalPlan = totalPlan.add(new BigDecimal(data.getJan().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getJan().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getFeb().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getFeb().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getMar().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getMar().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getApr().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getApr().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getMay().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getMay().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getJun().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getJun().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getJul().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getJul().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getAug().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getAug().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getSep().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getSep().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getOct().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getOct().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getNov().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getNov().getFact()));

        totalPlan = totalPlan.add(new BigDecimal(data.getDec().getPlan()));
        totalFact = totalFact.add(new BigDecimal(data.getDec().getFact()));

        System.out.println("Total Plan: " + totalPlan);
        System.out.println("Total Fact: " + totalFact);


        return getPlanFactDto(totalPlan, totalFact);
    }


    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto getPlanFactDifferenceDto(Payment payment) {
        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto planFactDto = null;

        if (isProcessed(payment)) {
            return createPlanFactDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO); // Return null or handle as needed
        }

        markAsProcessed(payment);

        if (payment.getSiblingId() != null) {
            Payment sibling = paymentService.getPaymentById(payment.getSiblingId());
            if (isProcessed(sibling)) {
                return createPlanFactDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO); // Return null or handle as needed
            }
            markAsProcessed(sibling);
            planFactDto = handleSiblingPayment(payment, sibling);
        } else {
            planFactDto = handleSinglePayment(payment);
        }

        return planFactDto;
    }

    private boolean isProcessed(Payment payment) {
        return processedPaymentIds.contains(payment.getId());
    }

    private void markAsProcessed(Payment payment) {
        processedPaymentIds.add(payment.getId());
    }

    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto handleSiblingPayment(Payment payment, Payment sibling) {
        BigDecimal fact = INITIAL_VALUE;
        BigDecimal plan = INITIAL_VALUE;

        if (payment.getVersionId() == VERSION_TWO) {
            plan = calculateDifference(payment.getAmountDt(), payment.getAmountKt());
            fact = calculateDifference(sibling.getAmountDt(), sibling.getAmountKt());
        } else {
            fact = calculateDifference(payment.getAmountDt(), payment.getAmountKt());
            plan = calculateDifference(sibling.getAmountDt(), sibling.getAmountKt());
        }

        return getPlanFactDto(plan, fact);
    }

    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto handleSinglePayment(Payment payment) {
        BigDecimal plan = calculateDifference(payment.getAmountDt(), payment.getAmountKt());
        BigDecimal fact = plan; // Since plan and fact are the same for single payment

        if (payment.getVersionId() == VERSION_TWO) {
            return createPlanFactDto(plan, INITIAL_VALUE, plan);
        } else {
            return createPlanFactDto(INITIAL_VALUE, fact, fact);
        }
    }

    private BigDecimal calculateDifference(BigDecimal amountDt, BigDecimal amountKt) {
        return amountDt.subtract(amountKt);
    }

    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto createPlanFactDto(BigDecimal plan, BigDecimal fact, BigDecimal difference) {
        return new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto(
                String.valueOf(plan),
                String.valueOf(fact),
                String.valueOf(difference));
    }

    private CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto getPlanFactDto(BigDecimal plan, BigDecimal fact) {
        BigDecimal difference = fact.subtract(plan);
        return createPlanFactDto(plan, fact, difference);
    }

}
