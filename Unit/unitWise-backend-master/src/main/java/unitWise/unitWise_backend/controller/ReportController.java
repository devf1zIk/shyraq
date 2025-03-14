package unitWise.unitWise_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitWise.unitWise_backend.dto.cashFlow.CashFlowByProjectResponseDto;
import unitWise.unitWise_backend.service.PaymentService;
import unitWise.unitWise_backend.service.ReportService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    private final PaymentService paymentService;
    private final ReportService reportService;

    public ReportController(PaymentService paymentService,
                            ReportService reportService) {
        this.paymentService = paymentService;
        this.reportService = reportService;
    }


    @GetMapping("/project-users/{id}")
    public String getProjectUsersById(@PathVariable Long id) {
        return """
                {
                  "project_id": 1,
                  "users": [
                    {
                      "user_id": 1,
                      "username": "john_doe",
                      "email": "john_doe@example.com",
                      "role": "admin",
                      "first_name": "John",
                      "last_name": "Doe",
                      "created_at": "2025-01-01T12:00:00Z",
                      "updated_at": "2025-02-01T12:00:00Z"
                    },
                    {
                      "user_id": 2,
                      "username": "jane_smith",
                      "email": "jane_smith@example.com",
                      "role": "developer",
                      "first_name": "Jane",
                      "last_name": "Smith",
                      "created_at": "2025-01-01T12:00:00Z",
                      "updated_at": "2025-02-01T12:00:00Z"
                    }
                    // Additional user objects
                  ]
                }
                """;
    }




    @GetMapping("/getCashFlow/{projectId}")
    public String getCashFlowByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year,
            @RequestParam(value = "unit_id", required = false) Long unitId,
            @RequestParam(value = "account_id", required = false)  Long accountId) {
        return """
                {
                   "project_id": 1,
                   "year": 2025,
                   "data": [
                     {
                       "name": "Операционные поступления",
                       "total": {
                         "plan": "1000",
                         "fact": "800",
                         "difference": "200"
                       },
                       "jan": {
                         "plan": "200",
                         "fact": "180",
                         "difference": "20"
                       },
                       "feb": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "mar": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "apr": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "may": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "jun": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "jul": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "aug": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "sep": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "oct": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "nov": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "dec": {
                         "plan": "100",
                         "fact": "80",
                         "difference": "20"
                       },
                       "expanded": false,
                       "children": [
                         {
                           "name": "Клиенты",
                           "total": {
                             "plan": "500",
                             "fact": "400",
                             "difference": "100"
                           },
                           "jan": {
                             "plan": "100",
                             "fact": "90",
                             "difference": "10"
                           },
                           "feb": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "mar": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "apr": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "may": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "jun": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "jul": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "aug": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "sep": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "oct": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "nov": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "dec": {
                             "plan": "50",
                             "fact": "40",
                             "difference": "10"
                           },
                           "expanded": false,
                           "children": [
                             {
                               "name": "KaraM_Реализация",
                               "total": {
                                 "plan": "250",
                                 "fact": "200",
                                 "difference": "50"
                               },
                               "jan": {
                                 "plan": "50",
                                 "fact": "45",
                                 "difference": "5"
                               },
                               "feb": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "mar": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "apr": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "may": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "jun": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "jul": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "aug": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "sep": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "oct": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "nov": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "dec": {
                                 "plan": "25",
                                 "fact": "20",
                                 "difference": "5"
                               },
                               "expanded": false,
                               "children": []
                             }
                           ]
                         }
                       ]
                     },
                     {
                       "name": "Операционные выбытия",
                       "total": {
                         "plan": "800",
                         "fact": "700",
                         "difference": "100"
                       },
                       "jan": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "feb": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "mar": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "apr": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "may": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "jun": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "jul": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "aug": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "sep": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "oct": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "nov": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "dec": {
                         "plan": "80",
                         "fact": "70",
                         "difference": "10"
                       },
                       "expanded": false,
                       "children": []
                     }
                   ]
                 }
                """;
    }

    @GetMapping("/getPnL/{projectId}")
    public ResponseEntity<CashFlowByProjectResponseDto> getPnLByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year,
            @RequestParam(value = "unit_id", required = false) Long unitId,
            @RequestParam(value = "account_id", required = false)  Long accountId) {
        return ResponseEntity.ok(reportService.mapPaymentsToCashFlowResponse(paymentService.getPaymentsByProjectIdAndYear(1L, 2025), 1L, 2025));
    }


    @GetMapping("/unitEc")
    public ResponseEntity<CashFlowByProjectResponseDto> getTest() {
        System.out.println("Before//////////////////////////////");
        System.out.println("P//////" + paymentService.getPaymentsByProjectIdAndYear(1L, 2025));
        System.out.println(reportService.mapPaymentsToCashFlowResponse(paymentService.getPaymentsByProjectIdAndYear(1L, 2025), 1L, 2025));
        System.out.println("After//////////////////////////////");

        return ResponseEntity.ok(reportService.mapPaymentsToCashFlowResponse(paymentService.getPaymentsByProjectIdAndYear(1L, 2025), 1L, 2025));
    }

//    @GetMapping("/unitEconomy/{projectId}")
//    public ResponseEntity<CashFlowByProjectResponseDto> getUnitEconomyByProject(
//            @PathVariable("projectId") Long projectId,
//            @RequestParam("year") int year,
//            @RequestParam(value = "unit_id", required = false) Long unitId,
//            @RequestParam(value = "account_id", required = false)  Long accountId) {
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto total =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("1000", "800", "200");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto jan =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("200", "180", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto feb =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto mar =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto apr =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto may =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto jun =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto jul =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto aug =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto sep =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto oct =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto nov =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//        CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto dec =
//                new CashFlowByProjectResponseDto.CashFlowDataDto.PlanFactDifferenceDto("100", "80", "20");
//
//        List<CashFlowByProjectResponseDto.CashFlowDataDto> childrenOfClients = Collections.singletonList(
//                new CashFlowByProjectResponseDto.CashFlowDataDto("KaraM_Реализация", total, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, false, Collections.emptyList())
//        );
//
//        List<CashFlowByProjectResponseDto.CashFlowDataDto> childrenOfOperatingReceipts = Collections.singletonList(
//                new CashFlowByProjectResponseDto.CashFlowDataDto("Клиенты", total, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, false, childrenOfClients)
//        );
//
//        CashFlowByProjectResponseDto.CashFlowDataDto operatingReceipts = new CashFlowByProjectResponseDto.CashFlowDataDto("Операционные поступления", total, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, false, childrenOfOperatingReceipts);
//        CashFlowByProjectResponseDto.CashFlowDataDto operatingExpenditures = new CashFlowByProjectResponseDto.CashFlowDataDto("Операционные выбытия", total, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, false, Collections.emptyList());
//
//        List<CashFlowByProjectResponseDto.CashFlowDataDto> data = Arrays.asList(operatingReceipts, operatingExpenditures);
//
//        System.out.println("Before//////////////////////////////");
//        System.out.println(reportService.mapPaymentsToCashFlowResponse(paymentService.getPaymentsByProjectIdAndYear(projectId, year), projectId, year));
//        System.out.println("After//////////////////////////////");
//
//        return ResponseEntity.ok(new CashFlowByProjectResponseDto(1L, 2025, data));
//
//    }

    @GetMapping("/unitEconomy/{projectId}")
    public ResponseEntity<CashFlowByProjectResponseDto> getUnitEconomyByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year) {

        return ResponseEntity.ok(reportService.mapPaymentsToCashFlowResponse(paymentService.getPaymentsByProjectIdAndYear(1L, 2025), 1L, 2025));
    }
}
