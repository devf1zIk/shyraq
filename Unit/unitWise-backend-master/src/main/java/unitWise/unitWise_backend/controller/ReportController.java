package unitWise.unitWise_backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unitWise.unitWise_backend.dto.report.ReportRequestDto;
import unitWise.unitWise_backend.dto.report.ReportResponseDto;
import unitWise.unitWise_backend.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    @Autowired
    private ReportService reportService;

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

    @PostMapping("/factCf")
    public ReportResponseDto create(@RequestBody List<ReportRequestDto> reportRequestDtoList) {
        return reportService.create(reportRequestDtoList.get(0));
    }

    @GetMapping("/factCf/getByProject/{projectId}")
    public String getFactCfByProject(@PathVariable("projectId") Long projectId,
                                     @RequestParam("version_id") Long versionId,
                                     @RequestParam("page") int page) {
        return """
                {
                  "project_id": 1,
                  "version_id": 1,
                  "page": 1,
                  "total_pages": 10,
                  "data": [
                    {
                      "date_accrual": "2025-01-01",
                      "date_transfer": "2025-01-02",
                      "company_id": 2,
                      "account_id": 2,
                      "contract_id": 2,
                      "amountdt": 1000.0,
                      "amountkt": 500.0,
                      "version_id": 1,
                      "user_id": 1,
                      "project_id": 1,
                      "comment": "Initial payment",
                      "created_at": "2025-01-01T12:00:00Z",
                      "updated_at": "2025-02-01T12:00:00Z"
                    },
                    {
                      "date_accrual": "2025-01-03",
                      "date_transfer": "2025-01-04",
                      "company_id": 2,
                      "account_id": 2,
                      "contract_id": 2,
                      "amountdt": 2000.0,
                      "amountkt": 1000.0,
                      "version_id": 1,
                      "user_id": 1,
                      "project_id": 1,
                      "comment": "Second payment",
                      "created_at": "2025-01-03T12:00:00Z",
                      "updated_at": "2025-02-03T12:00:00Z"
                    }
                    // Additional financial records
                  ]
                }
                """;
    }

    @GetMapping("/unitEconomy/{projectId}")
    public String getUnitEconomy(@PathVariable("projectId") Long projectId,
                                 @RequestParam("year") int year){
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

    @GetMapping("/PnL/{projectId}")
    public String getPnL(@PathVariable("projectId") Long projectId,
                         @RequestParam("year") int year){
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


    @GetMapping("/getCashFlow/{projectId}")
    public String getCashFlowByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year) {
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

}
