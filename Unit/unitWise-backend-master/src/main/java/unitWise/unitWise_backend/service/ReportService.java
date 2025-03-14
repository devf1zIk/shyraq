package unitWise.unitWise_backend.service;

import org.springframework.stereotype.Service;
import unitWise.unitWise_backend.dto.cashFlow.CashFlowByProjectResponseDto;
import unitWise.unitWise_backend.entity.Article;
import unitWise.unitWise_backend.entity.Payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ArticleService articleService;

    public ReportService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public CashFlowByProjectResponseDto mapPaymentsToCashFlowResponse(List<Payment> payments, Long projectId, int year) {
        CashFlowByProjectResponseDto response = new CashFlowByProjectResponseDto();
        response.setProjectId(projectId);
        response.setYear(year);

        List<CashFlowByProjectResponseDto.CashFlowDataDto> cashFlowDataList = new ArrayList<>();

        Map<String, CashFlowByProjectResponseDto.CashFlowDataDto> categoryMap = new HashMap<>();

        for (Payment payment : payments) {
            if (payment.getDateAccrual() == null) continue;

            String month = new SimpleDateFormat("MMM").format(payment.getDateAccrual()).toLowerCase(); // jan, feb, mar...

            // Example category name, you can customize this logic
            String categoryName = articleService.getArticleById(payment.getArticleId()).getCategory();

            categoryMap.putIfAbsent(categoryName, new CashFlowByProjectResponseDto.CashFlowDataDto(
                    categoryName,
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("0", "0", "0"),
                    false,
                    new ArrayList<>()
            ));

            CashFlowByProjectResponseDto.CashFlowDataDto dataDto = categoryMap.get(categoryName);

            CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto planFactDto =
                    new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto(
                            String.valueOf(payment.getAmountDt()),
                            String.valueOf(payment.getAmountKt()),
                            String.valueOf(payment.getAmountDt() - payment.getAmountKt()));

            switch (month) {
                case "jan": dataDto.setJan(planFactDto); break;
                case "feb": dataDto.setFeb(planFactDto); break;
                case "mar": dataDto.setMar(planFactDto); break;
                case "apr": dataDto.setApr(planFactDto); break;
                case "may": dataDto.setMay(planFactDto); break;
                case "jun": dataDto.setJun(planFactDto); break;
                case "jul": dataDto.setJul(planFactDto); break;
                case "aug": dataDto.setAug(planFactDto); break;
                case "sep": dataDto.setSep(planFactDto); break;
                case "oct": dataDto.setOct(planFactDto); break;
                case "nov": dataDto.setNov(planFactDto); break;
                case "dec": dataDto.setDec(planFactDto); break;
            }
        }

        cashFlowDataList.addAll(categoryMap.values());
        response.setData(cashFlowDataList);

        return response;
    }

}
