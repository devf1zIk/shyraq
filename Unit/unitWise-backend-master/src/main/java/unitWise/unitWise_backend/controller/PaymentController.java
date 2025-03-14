package unitWise.unitWise_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unitWise.unitWise_backend.dto.cashFlow.PaymentRequestDto;
import unitWise.unitWise_backend.dto.cashFlow.PaymentResponseDto;
import unitWise.unitWise_backend.service.PaymentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {


    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/factCf/getByProject/{projectId}")
    public PaymentResponseDto getFactCfByProject(@PathVariable("projectId") Long projectId,
                                                 @RequestParam("version_id") Long versionId,
                                                 @RequestParam("page") int page) {
        System.out.println(paymentService.getPaymentsByProjectIdAndYear(projectId, 2025));
        return paymentService.getPaymentsByProject(projectId);
    }

    @PostMapping("/bankStatement/uniquePDFNames/{projectId}/{userId}")
    public ResponseEntity<String> uploadDto(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ){
        try {
            paymentService.processFile(file, projectId, userId);
            return ResponseEntity.ok("Файл обработан и данные сохранены в БД!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }


//    @GetMapping("/factCf/getByProject/{projectId}")
//    public String getFactCfByProject(@PathVariable("projectId") Long projectId,
//                                     @RequestParam("version_id") Long versionId,
//                                     @RequestParam("page") int page) {
//        return """
//                {
//                  "data": {
//                    "factCfRecords": {
//                      "data": [
//                        {
//                          "id": 1,
//                          "date_accrual": "2025-01-01",
//                          "date_transfer": "2025-01-02",
//                          "company_id": 1,
//                          "account_id": 1,
//                          "contract_id": 1,
//                          "amountdt": 1000.00,
//                          "amountkt": 500.00,
//                          "cf_item_id": 3,
//                          "version_id": 1,
//                          "user_id": 1,
//                          "project_id": 1,
//                          "comment": "Initial payment",
//                          "created_at": "2025-01-01T12:00:00Z",
//                          "updated_at": "2025-01-02T12:00:00Z"
//                        },
//                        {
//                          "id": 2,
//                          "date_accrual": "2025-01-05",
//                          "date_transfer": "2025-01-06",
//                          "company_id": 2,
//                          "account_id": 2,
//                          "contract_id": 2,
//                          "amountdt": 2000.00,
//                          "amountkt": 1000.00,
//                          "cf_item_id": 1,
//                          "version_id": 1,
//                          "user_id": 1,
//                          "project_id": 2,
//                          "comment": "Second payment",
//                          "created_at": "2025-01-05T12:00:00Z",
//                          "updated_at": "2025-01-06T12:00:00Z"
//                        },
//                        {
//                          "id": 3,
//                          "date_accrual": "2025-02-01",
//                          "date_transfer": "2025-02-02",
//                          "company_id": 3,
//                          "account_id": 3,
//                          "contract_id": 3,
//                          "amountdt": 3000.00,
//                          "amountkt": 1500.00,
//                          "cf_item_id": 1,
//                          "version_id": 2,
//                          "user_id": 2,
//                          "project_id": 1,
//                          "comment": "Third payment",
//                          "created_at": "2025-02-01T12:00:00Z",
//                          "updated_at": "2025-02-02T12:00:00Z"
//                        },
//                        {
//                          "id": 4,
//                          "date_accrual": "2025-02-10",
//                          "date_transfer": "2025-02-11",
//                          "company_id": 1,
//                          "account_id": 2,
//                          "contract_id": 4,
//                          "amountdt": 4000.00,
//                          "amountkt": 2000.00,
//                          "cf_item_id": 5,
//                          "version_id": 2,
//                          "user_id": 3,
//                          "project_id": 2,
//                          "comment": "Fourth payment",
//                          "created_at": "2025-02-10T12:00:00Z",
//                          "updated_at": "2025-02-11T12:00:00Z"
//                        },
//                        {
//                          "id": 5,
//                          "date_accrual": "2025-03-01",
//                          "date_transfer": "2025-03-02",
//                          "company_id": 4,
//                          "account_id": 3,
//                          "contract_id": 5,
//                          "amountdt": 5000.00,
//                          "amountkt": 2500.00,
//                          "cf_item_id": 5,
//                          "version_id": 3,
//                          "user_id": 4,
//                          "project_id": 1,
//                          "comment": "Fifth payment",
//                          "created_at": "2025-03-01T12:00:00Z",
//                          "updated_at": "2025-03-02T12:00:00Z"
//                        }
//                      ],
//                      "last_page": 1
//                    },
//                    "totalAmountDt": 15000.00,
//                    "totalAmountKt": 7500.00,
//                    "totalSum": 7500.00
//                  }
//                }
//                """;
//    }




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

    @DeleteMapping("/factCf/delete")
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
