package unitWise.unitWise_backend.service;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.svg.converter.SvgConverter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unitWise.unitWise_backend.dto.cashFlow.PaymentRequestDto;
import unitWise.unitWise_backend.dto.cashFlow.PaymentResponseDto;
import unitWise.unitWise_backend.entity.*;
import unitWise.unitWise_backend.repository.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private static final String UPLOAD_DIR = "uploads/";

    private final PaymentRepository paymentRepository;
    private final ContractService contractService;
    private final BusinessUnitRepository businessUnitRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final BankAccountRepository bankAccountRepository;


    public PaymentService(PaymentRepository paymentRepository,
                          ContractService contractService,
                          CurrencyConverter currencyConverter, BusinessUnitRepository businessUnitRepository, ContractRepository contractRepository, UserRepository userRepository, ArticleRepository articleRepository, BankAccountRepository bankAccountRepository) {
        this.paymentRepository = paymentRepository;
        this.contractService = contractService;
        this.businessUnitRepository = businessUnitRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public void createPayment(PaymentRequestDto paymentRequestDto) {
        Contract contract = contractRepository.getById(paymentRequestDto.getContractId());
        Long articleId = contract.getCfItemId();
        Article article = articleRepository.getById(articleId);

        BusinessUnit company = businessUnitRepository.getById(paymentRequestDto.getCompanyId());
        BankAccount account = bankAccountRepository.getById(paymentRequestDto.getAccountId());
        User user = userRepository.getById(paymentRequestDto.getUserId());

        Payment payment = Payment.builder()
                .dateAccrual(paymentRequestDto.getDateAccrual())
                .dateTransfer(paymentRequestDto.getDateTransfer())
                .companyId(company.getId())
                .accountId(account.getId())
                .contractId(contract.getId())
                .amountDt(paymentRequestDto.getAmountDt())
                .amountKt(paymentRequestDto.getAmountKt())
                .articleId(article.getId())
                .versionId(paymentRequestDto.getVersionId())
                .userId(user.getId())
                .projectId(paymentRequestDto.getProjectId())
                .comment(paymentRequestDto.getComment())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        setSiblingsId(savedPayment);
    }


    public void deletePayments(List<Long> paymentIds) {
        for (Long id : paymentIds) {
            paymentRepository.deleteById(id);
        }
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void updateCashFlow(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentRepository.findById(paymentRequestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentRequestDto.getId()));

        Contract contract = contractRepository.getById(paymentRequestDto.getContractId());
        Article article = articleRepository.getById(paymentRequestDto.getArticleId());
        BusinessUnit company = businessUnitRepository.getById(paymentRequestDto.getCompanyId());
        BankAccount account = bankAccountRepository.getById(paymentRequestDto.getAccountId());
        User user = userRepository.getById(paymentRequestDto.getUserId());

        payment.setDateAccrual(paymentRequestDto.getDateAccrual());
        payment.setDateTransfer(paymentRequestDto.getDateTransfer());
        payment.setCompanyId(company.getId());
        payment.setAccountId(account.getId());
        payment.setContractId(contract.getId());
        payment.setAmountDt(paymentRequestDto.getAmountDt());
        payment.setAmountKt(paymentRequestDto.getAmountKt());
        payment.setArticleId(article.getId());
        payment.setVersionId(paymentRequestDto.getVersionId());
        payment.setUserId(user.getId());
        payment.setProjectId(paymentRequestDto.getProjectId());
        payment.setComment(paymentRequestDto.getComment());

        paymentRepository.save(payment);
    }


    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow();
    }


    @Transactional
    public PaymentResponseDto getPaymentByProject(Long projectId, Long versionId, int page,
                                                  LocalDate dateAccrualStart, LocalDate dateAccrualEnd,
                                                  LocalDate dateTransferStart, LocalDate dateTransferEnd,
                                                  List<Long> companyIds, Long articleId, Long contractId, Long accountId,
                                                  Double amountFrom, Double amountTo) {

        PageRequest pageable = PageRequest.of(page, 50); // Adjust page size as needed
        Page<Payment> paymentPage = paymentRepository.findPaymentsByFilters(
                projectId, versionId, dateAccrualStart, dateAccrualEnd,
                dateTransferStart, dateTransferEnd, companyIds, articleId, contractId, accountId,
                amountFrom, amountTo, pageable
        );

        List<Payment> payments = paymentPage.getContent();
        PaymentResponseDto.FactCfData data = calculateTotalAmounts(payments);

        return new PaymentResponseDto(data);
    }


    public List<Payment> getPaymentsByProjectIdAndFilters(Long projectId, int year, List<Long> unitIds, int versionId) {
        List<Payment> payments = paymentRepository.findPaymentsByProjectIdAndYearAndCompanyIdsAndVersionId(projectId, year, unitIds, versionId);
        return payments;
    }
    public List<Payment> getPaymentsByProjectIdAndFilters(Long projectId, int year,  int versionId) {
        List<Payment> payments = paymentRepository.findPaymentsByProjectIdAndYearAndVersionId(projectId, year, versionId);

        return payments;
    }

    public List<Payment> getPaymentsByProjectIdAndYearAndUnitIds(Long projectId, int year, List<Long> unitIds) {
        List<Payment> payments = paymentRepository.findPaymentsByProjectIdAndYearAndCompanyIds(projectId, year, unitIds);

        return payments;
    }


    public List<Payment> getPaymentsByProjectIdAndYear(Long projectId, int year, List<Long> unitIds,  Long accountId) {
        return paymentRepository
                .findPaymentsByProjectAndYear(projectId, year, unitIds, accountId);
    }


    public PaymentResponseDto.FactCfData calculateTotalAmounts(List<Payment> payments) {

        BigDecimal totalAmountDt = calculateTotalAmountDt(payments);
        BigDecimal totalAmountKt = calculateTotalAmountKt(payments);
        BigDecimal totalSum = totalAmountDt.subtract(totalAmountKt);

        PaymentResponseDto.FactCfRecords records = new PaymentResponseDto.FactCfRecords(payments, 1);

        return new PaymentResponseDto.FactCfData(records, totalAmountDt, totalAmountKt, totalSum);
    }
    public BigDecimal calculateTotalAmountDt(List<Payment> payments) {
        return payments.stream()
                .map(Payment::getAmountDt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalAmountKt(List<Payment> payments) {
        return payments.stream()
                .map(Payment::getAmountKt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Payment> processFile(MultipartFile file, Long projectId, Long userId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Файл пуст!");
        }

        Path uploadDir = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path savePath = uploadDir.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

        if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx") || file.getOriginalFilename().toLowerCase().endsWith(".xls")) {
            return parsetoPdf(savePath.toString(), projectId, userId);
        } else {
            throw new IOException("Неподдерживаемый формат файла: " + file.getOriginalFilename());
        }

    }

    private List<Payment> parsetoPdf(String string, Long projectId, Long userId) {
        return null;
    }

    private void setSiblingsId(Payment payment) {
        List<Payment> siblings = paymentRepository
                .findPaymentsByIds(
                        payment.getProjectId(),
                        payment.getAccountId(),
                        payment.getCompanyId(),
                        payment.getArticleId(),
                        payment.getVersionId());

        if (siblings != null && !siblings.isEmpty()) {
            payment.setSiblingId(siblings.get(0).getId());
            siblings.get(0).setSiblingId(payment.getId());

            paymentRepository.save(payment);
            paymentRepository.save(siblings.get(0));
        }
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + email));
    }

    public byte[] exportToPdf(List<Payment> payments) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Настройка PDF
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            User user = getCurrentUser();

            // Логотип
            InputStream svgStream = getClass().getClassLoader()
                    .getResourceAsStream("images/logo.svg");
            if (svgStream != null) {
                Image logo = SvgConverter.convertToImage(svgStream, pdf);
                logo.setWidth(150);
                logo.setHorizontalAlignment(HorizontalAlignment.LEFT);
                document.add(logo);
            }

            // Заголовок отчёта
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            Paragraph reportTitle = new Paragraph("ОТЧЕТ — " + user.getEmail())
                    .setFont(bold)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginBottom(5);
            document.add(reportTitle);

            // ФИО пользователя
            Paragraph userName = new Paragraph(user.getFullName())
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginBottom(15);
            document.add(userName);

            // Создаём таблицу из 10 столбцов
            float[] columnWidths = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .useAllAvailableWidth()
                    .setFontSize(9);

            // Заголовки столбцов
            String[] headers = {
                    "Дата начисления", "Дата перечисления", "Компания", "Статья",
                    "Контракт", "Счет", "Сумма Дт", "Сумма Кт", "Версия", "Пользователь"
            };
            for (String h : headers) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(h).setFont(bold))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setPadding(5));
            }

            // Обозначаем, что первая строка — шапка, и повторяем её на новых страницах
            table.setSkipFirstHeader(false);

            // Заполняем тело таблицы
            boolean isOdd = true;
            for (Payment p : payments) {
                Contract    contract = contractRepository.getById(p.getContractId());
                Article      article = articleRepository.getById(p.getArticleId());
                BusinessUnit company = businessUnitRepository.getById(p.getCompanyId());
                BankAccount   account = bankAccountRepository.getById(p.getAccountId());
                Color bg = isOdd ? ColorConstants.WHITE : new DeviceRgb(240, 240, 240);
                isOdd = !isOdd;

                table.addCell(getCell(p.getDateAccrual(), bg));
                table.addCell(getCell(p.getDateTransfer(), bg));
                table.addCell(getCell(company.getName(), bg));
                table.addCell(getCell(article.getItem(), bg));
                table.addCell(getCell(contract.getShortName(), bg));
                table.addCell(getCell(account.getName(), bg));
                table.addCell(getCell(p.getAmountDt(), bg, true));
                table.addCell(getCell(p.getAmountKt(), bg, true));
                table.addCell(getCell(
                        p.getVersionId() != null ? p.getVersionId().toString() : "-", bg));
                table.addCell(getCell(
                        user.getFullName() != null ? user.getFullName() : "-", bg));
            }

            // Добавляем таблицу в документ и закрываем
            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при экспорте PDF: " + e.getMessage(), e);
        }
    }


    private String trimText(String text, int maxLength) {
        if (text == null) return "-";
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }

    private Cell getCell(Object value, Color bgColor) {
        return new Cell()
                .add(new Paragraph(value != null ? value.toString() : ""))
                .setBackgroundColor(bgColor)
                .setPadding(5);
    }

    private Cell getCell(Object value, Color backgroundColor, boolean alignRight) {
        String text = "-";
        if (value instanceof LocalDate) {
            text = value.toString();
        } else if (value instanceof BigDecimal) {
            text = ((BigDecimal) value).setScale(2, RoundingMode.HALF_UP).toString();
        } else if (value != null) {
            text = value.toString();
        }

        TextAlignment alignment = alignRight ? TextAlignment.RIGHT : TextAlignment.CENTER;

        return new Cell()
                .add(new Paragraph(text))
                .setBackgroundColor(backgroundColor)
                .setTextAlignment(alignment)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }
}
