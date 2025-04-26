package unitWise.unitWise_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitWise.unitWise_backend.dto.ProjectUsersDto;
import unitWise.unitWise_backend.dto.report.CashFlowByProjectResponseDto;
import unitWise.unitWise_backend.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Отчеты", description = "Управление отчетами")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Получить пользователей проекта", description = "Возвращает пользователей проекта по ID проекта")
    @GetMapping("/project-users/{id}")
    public ProjectUsersDto getProjectUsersById(@PathVariable Long id) {
        return new ProjectUsersDto(); // empty dto
    }

    @Operation(summary = "Получить денежный поток по проекту", description = "Возвращает данные о денежном потоке по ID проекта и году, а также опционально по ID единицы и ID аккаунта")
    @GetMapping("/getCashFlow/{projectId}")
    public ResponseEntity<CashFlowByProjectResponseDto> getCashFlowByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds,
            @RequestParam(value = "account_id", required = false)  Long accountId) {
        return ResponseEntity.ok(reportService.getReport(projectId, year, unitIds, accountId));
    }

    @Operation(summary = "Получить P&L по проекту", description = "Возвращает данные о прибылях и убытках (P&L) по ID проекта и году, а также опционально по ID единицы и ID аккаунта")
    @GetMapping("/getPnL/{projectId}")
    public ResponseEntity<CashFlowByProjectResponseDto> getPnLByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds,
            @RequestParam(value = "account_id", required = false)  Long accountId) {
        return ResponseEntity.ok(reportService.getReport(projectId, year, unitIds, accountId));
    }

    @Operation(summary = "Получить экономику единицы по проекту", description = "Возвращает данные об экономике единицы по ID проекта и году, а также опционально по ID единицы и ID аккаунта")
    @GetMapping("/unitEconomy/{projectId}")
    public ResponseEntity<CashFlowByProjectResponseDto> getUnitEconomyByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("year") int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds,
            @RequestParam(value = "account_id", required = false)  Long accountId) {
        return ResponseEntity.ok(reportService.getReport(projectId, year, unitIds, accountId));
    }
}