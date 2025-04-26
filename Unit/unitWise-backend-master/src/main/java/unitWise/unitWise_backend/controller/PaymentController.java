package unitWise.unitWise_backend.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unitWise.unitWise_backend.dto.cashFlow.PaymentRequestDto;
import unitWise.unitWise_backend.dto.cashFlow.PaymentResponseDto;
import unitWise.unitWise_backend.entity.Payment;
import unitWise.unitWise_backend.repository.PaymentRepository;
import unitWise.unitWise_backend.service.PaymentService;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/factCf")
@Tag(name = "Платежи", description = "Управление платежами")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService,
                             PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    @Operation(summary = "Получить платежи по проекту", description = "Возвращает платежи по ID проекта, версии, странице и опционально другим параметрам")
    @GetMapping("/getByProject/{projectId}")
    public PaymentResponseDto getFactCfByProject(@PathVariable("projectId") Long projectId,
                                                 @RequestParam("version_id") Long versionId,
                                                 @RequestParam("page") int page,
                                                 @RequestParam(value = "date_accrual_start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAccrualStart,
                                                 @RequestParam(value = "date_accrual_end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAccrualEnd,
                                                 @RequestParam(value = "date_transfer_start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransferStart,
                                                 @RequestParam(value = "date_transfer_end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransferEnd,
                                                 @RequestParam(value = "company_id", required = false) List<Long> companyIds,
                                                 @RequestParam(value = "cf_item_id", required = false) Long articleId,
                                                 @RequestParam(value = "contract_id", required = false) Long contractId,
                                                 @RequestParam(value = "account_id", required = false) Long accountId,
                                                 @RequestParam(value = "amount_from", required = false) Double amountFrom,
                                                 @RequestParam(value = "amount_to", required = false) Double amountTo) {
        return paymentService.getPaymentByProject(
                projectId, versionId, page - 1, dateAccrualStart, dateAccrualEnd,
                dateTransferStart, dateTransferEnd, companyIds, articleId, contractId, accountId, amountFrom, amountTo
        );
    }

    @Operation(summary = "Создать платежи", description = "Создает несколько платежей")
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody List<PaymentRequestDto> paymentRequestDtos) {
        try {
            for (PaymentRequestDto paymentRequestDto : paymentRequestDtos) {
                System.out.println(paymentRequestDto);
                paymentService.createPayment(paymentRequestDto);
            }
            return ResponseEntity.ok().body("Cash flow records created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Загрузить файл выписки банка", description = "Загружает файл выписки банка и обрабатывает его")
    @PostMapping("/bankStatement/uniquePDFNames/{projectId}/{userId}")
    public ResponseEntity<List<Payment>> uploadDto(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<Payment> payments = paymentService.processFile(file, projectId, userId);
            return ResponseEntity.ok(payments);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Загрузить данные выписки", description = "Загружает данные выписки и сохраняет их")
    @PostMapping("/vpiska")
    public ResponseEntity<List<Payment>> uploadVpiska(@RequestBody List<Payment> payments) {
        System.out.println(payments);

        List<Payment> savedPayments = new ArrayList<>();
        for (Payment payment : payments) {
            Payment savedPayment = paymentService.savePayment(payment);
            savedPayments.add(savedPayment);
        }

        return ResponseEntity.ok(savedPayments);
    }

    @Operation(summary = "Экспортировать платежи как PDF", description = "Экспортирует платежи по ID проекта в формате PDF")
    @GetMapping("/export/pdf/{projectId}")
    public ResponseEntity<byte[]> exportPaymentsAsPdf(@PathVariable Long projectId) {
        System.out.println("projectId: " + projectId);
        log.info("Attempting to export payments as PDF for project ID: {}", projectId);
        List<Payment> payments = paymentRepository.findByProjectId(projectId);
        if (payments.isEmpty()) {
            log.info("No payments found for project ID: {}", projectId);
            return ResponseEntity.noContent().build();
        }

        byte[] pdfFile = paymentService.exportToPdf(payments);

        String filename = "Платежи.pdf";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile);

    }

    @Operation(summary = "Обновить платежи", description = "Обновляет несколько платежей")
    @PostMapping("/update")
    public ResponseEntity<?> updateCashFlow(@RequestBody List<PaymentRequestDto> paymentRequestDtos) {
        try {
            System.out.println("Begging Update");
            for (PaymentRequestDto paymentRequestDto : paymentRequestDtos) {
                System.out.println(paymentRequestDto);
                paymentService.updateCashFlow(paymentRequestDto);
            }
            return ResponseEntity.ok().body("Cash flow records updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Удалить платежи", description = "Удаляет несколько платежей по их ID")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody List<Long> paymentIds) {
        try {
            paymentService.deletePayments(paymentIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting payments");
        }
    }
}