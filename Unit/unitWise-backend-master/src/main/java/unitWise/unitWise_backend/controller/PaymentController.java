package unitWise.unitWise_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unitWise.unitWise_backend.dto.cashFlow.PaymentRequestDto;
import unitWise.unitWise_backend.dto.cashFlow.PaymentResponseDto;
import unitWise.unitWise_backend.dto.vypiska.PaymentRequestDTO;
import unitWise.unitWise_backend.dto.vypiska.PaymentResponseDTO;
import unitWise.unitWise_backend.entity.Payment;
import unitWise.unitWise_backend.repository.PaymentRepository;
import unitWise.unitWise_backend.service.PaymentService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {


    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;


    public PaymentController(PaymentService paymentService, PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }


    @GetMapping("/factCf/getByProject/{projectId}")
    public PaymentResponseDto getFactCfByProject(@PathVariable("projectId") Long projectId,
                                                 @RequestParam("version_id") Long versionId,
                                                 @RequestParam("page") int page) {
        System.out.println(paymentService.getPaymentsByProjectIdAndYear(projectId, 2025));
        return paymentService.getPaymentsByProject(projectId);
    }

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

    @PostMapping("/factCf/vpiska")
    public ResponseEntity<List<Payment>> uploadVpiska(@RequestBody List<Payment> payments) {
        System.out.println(payments);

        List<Payment> savedPayments = new ArrayList<>();
        for (Payment payment : payments) {
            Payment savedPayment = paymentService.savePayment(payment);
            savedPayments.add(savedPayment);
        }

        return ResponseEntity.ok(savedPayments);
    }


    @GetMapping("/factCf/export/{projectId}")
    public ResponseEntity<byte[]> exportPaymentsAsExcel(@PathVariable Long projectId) {
        log.info("Attempting to export payments for project ID: {}", projectId);
        try {
            List<Payment> payments = paymentRepository.findByProjectId(projectId);
            if (payments.isEmpty()) {
                log.info("No payments found for project ID: {}", projectId);
                return ResponseEntity.noContent().build();
            }
            byte[] excelFile = paymentService.saveToExcel(payments);
            log.info("Export successful for project ID: {}", projectId);

            HttpHeaders headers = new HttpHeaders();
            String filename = "Платежи.xlsx";
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excelFile);
        } catch (IOException e) {
            log.error("Error exporting payments: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/factCf")
    public ResponseEntity<?> createCashFlow(@RequestBody List<PaymentRequestDto> paymentRequestDtos) {
        try {
            for (PaymentRequestDto paymentRequestDto : paymentRequestDtos) {
                System.out.println(paymentRequestDto);
                paymentService.createCashFlow(paymentRequestDto);
            }
            return ResponseEntity.ok().body("Cash flow records created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/factCf/delete")
    public ResponseEntity<?> delete(@RequestParam("ids") List<Long> paymentIds) {
        log.info("received delete paymentIds " + paymentIds);
        try {
            paymentService.deletePayments(paymentIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting payments");
        }
    }
}
