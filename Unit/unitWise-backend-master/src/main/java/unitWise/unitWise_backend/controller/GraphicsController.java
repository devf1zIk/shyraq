package unitWise.unitWise_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitWise.unitWise_backend.dto.graphics.MoneyBalanceState;
import unitWise.unitWise_backend.service.GraphicsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Графики/Дашборд", description = "Управление графиками")
public class GraphicsController {

    private final GraphicsService graphicsService;

    public GraphicsController(GraphicsService graphicsService) {
        this.graphicsService = graphicsService;
    }

    @Operation(summary = "Получить баланс денег", description = "Возвращает баланс денег по ID проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/factCf/getMoneyBalance/{projectId}")
    public ResponseEntity<List<MoneyBalanceState>> getMoneyBalance(
            @PathVariable Long projectId,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        List<MoneyBalanceState> moneyBalance = graphicsService.getMoneyBalance(projectId, year, month, unitIds);
        return ResponseEntity.ok(moneyBalance);
    }

    @Operation(summary = "Получить данные о прибыли", description = "Возвращает данные о прибыли по имени проекта, году и опционально ID единицы")
    @GetMapping("/modelE/getProfitData/{projectName}")
    public ResponseEntity<Object> getProfitData(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object profitData = graphicsService.getProfitData(projectName, year, unitIds);
        return ResponseEntity.ok(profitData);
    }

    @Operation(summary = "Получить движение денег", description = "Возвращает данные о движении денег по имени проекта, году и опционально ID единицы")
    @GetMapping("/modelE/getMoneyMovement/{projectName}")
    public ResponseEntity<Object> getMoneyMovement(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object moneyMovement = graphicsService.getMoneyMovement(projectName, year, unitIds);
        return ResponseEntity.ok(moneyMovement);
    }

    @Operation(summary = "Получить доход по направлению", description = "Возвращает данные о доходе по направлению по имени проекта, году и опционально месяцу")
    @GetMapping("/modelE/getIncomeByDirection/{projectName}")
    public ResponseEntity<Object> getIncomeByDirection(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month) {
        Object incomeByDirection = graphicsService.getIncomeByDirection(projectName, year, month);
        return ResponseEntity.ok(incomeByDirection);
    }

    @Operation(summary = "Получить прибыль по проекту", description = "Возвращает данные о прибыли по проекту по имени проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/modelE/getProfitByProject/{projectName}")
    public ResponseEntity<Object> getProfitByProject(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object profitByProject = graphicsService.getProfitByProject(projectName, year, month, unitIds);
        return ResponseEntity.ok(profitByProject);
    }

    @Operation(summary = "Получить продажи по проекту", description = "Возвращает данные о продажах по проекту по имени проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/modelE/getSalesByProject/{projectName}")
    public ResponseEntity<Object> getSalesByProject(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object salesByProject = graphicsService.getSalesByProject(projectName, year, month, unitIds);
        return ResponseEntity.ok(salesByProject);
    }

    @Operation(summary = "Получить остатки", description = "Возвращает данные об остатках по имени проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/modelE/getRemainers/{projectName}")
    public ResponseEntity<Object> getRemainers(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object remainers = graphicsService.getRemainers(projectName, year, month, unitIds);
        return ResponseEntity.ok(remainers);
    }

    @Operation(summary = "Получить доход по категории", description = "Возвращает данные о доходе по категории по имени проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/modelE/getIncomeByCategory/{projectName}")
    public ResponseEntity<Object> getIncomeByCategory(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object incomeByCategory = graphicsService.getIncomeByCategory(projectName, year, month, unitIds);
        return ResponseEntity.ok(incomeByCategory);
    }

    @Operation(summary = "Получить расходы по категории", description = "Возвращает данные о расходах по категории по имени проекта, году и опционально месяцу и ID единицы")
    @GetMapping("/modelE/getExpensesByCategory/{projectName}")
    public ResponseEntity<Object> getExpensesByCategory(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object expensesByCategory = graphicsService.getExpensesByCategory(projectName, year, month, unitIds);
        return ResponseEntity.ok(expensesByCategory);
    }

    @Operation(summary = "Получить доход по дате", description = "Возвращает данные о доходе по дате по имени проекта, году и опционально ID единицы")
    @GetMapping("/modelE/getIncomeByDate/{projectName}")
    public ResponseEntity<Object> getIncomeByDate(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(value = "unit_id", required = false) List<Long> unitIds) {
        Object incomeByDate = graphicsService.getIncomeByDate(projectName, year, unitIds);
        return ResponseEntity.ok(incomeByDate);
    }
}