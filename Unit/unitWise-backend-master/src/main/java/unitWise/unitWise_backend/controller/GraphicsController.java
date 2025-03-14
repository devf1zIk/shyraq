package unitWise.unitWise_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitWise.unitWise_backend.dto.DataResponse;
import unitWise.unitWise_backend.dto.graphics.MoneyBalanceState;
import unitWise.unitWise_backend.service.GraphicsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GraphicsController {

    @Autowired
    private GraphicsService graphicsService;

    @GetMapping("/factCf/getMoneyBalance/{projectId}")
    public ResponseEntity<List<MoneyBalanceState>> getMoneyBalance(
            @PathVariable Long projectId,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        List<MoneyBalanceState> moneyBalance = graphicsService.getMoneyBalance(projectId, year, month, unitIds);
        return ResponseEntity.ok(moneyBalance);
    }

    @GetMapping("/modelE/getProfitData/{projectName}")
    public ResponseEntity<String> getProfitData(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) List<Long> unitIds) {
        Object profitData = graphicsService.getProfitData(projectName, year, unitIds);
        return ResponseEntity.ok("""
                [
                   {
                     "date": "2025-01-01",
                     "id": 1,
                     "income_loss": 5000.0,
                     "marginality": "high"
                   },
                   {
                     "date": "2025-02-01",
                     "id": 2,
                     "income_loss": 7000.0,
                     "marginality": "medium"
                   },
                   {
                     "date": "2025-03-01",
                     "id": 3,
                     "income_loss": 3000.0,
                     "marginality": "low"
                   },
                   {
                     "date": "2025-04-01",
                     "id": 4,
                     "income_loss": 6000.0,
                     "marginality": "medium"
                   },
                   {
                     "date": "2025-05-01",
                     "id": 5,
                     "income_loss": 8000.0,
                     "marginality": "high"
                   },
                   {
                     "date": "2025-06-01",
                     "id": 6,
                     "income_loss": 4000.0,
                     "marginality": "low"
                   },
                   {
                     "date": "2025-07-01",
                     "id": 7,
                     "income_loss": 9000.0,
                     "marginality": "high"
                   },
                   {
                     "date": "2025-08-01",
                     "id": 8,
                     "income_loss": 10000.0,
                     "marginality": "high"
                   },
                   {
                     "date": "2025-09-01",
                     "id": 9,
                     "income_loss": 11000.0,
                     "marginality": "medium"
                   },
                   {
                     "date": "2025-10-01",
                     "id": 10,
                     "income_loss": 12000.0,
                     "marginality": "high"
                   }
                 ]
                """);
    }

    @GetMapping("/modelE/getMoneyMovement/{projectName}")
    public ResponseEntity<String> getMoneyMovement(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) List<Long> unitIds) {
        Object moneyMovement = graphicsService.getMoneyMovement(projectName, year, unitIds);
//        return ResponseEntity.ok(moneyMovement);
        return ResponseEntity.ok("""
                [
                  {
                    "date": "2025-01-01",
                    "id": 1,
                    "income_sum": 3000.0,
                    "outcome_sum": 1500.0,
                    "remainder": 1500.0
                  },
                  {
                    "date": "2025-02-01",
                    "id": 2,
                    "income_sum": 4000.0,
                    "outcome_sum": 2000.0,
                    "remainder": 2000.0
                  },
                  {
                    "date": "2025-03-01",
                    "id": 3,
                    "income_sum": 5000.0,
                    "outcome_sum": 2500.0,
                    "remainder": 2500.0
                  },
                  {
                    "date": "2025-04-01",
                    "id": 4,
                    "income_sum": 6000.0,
                    "outcome_sum": 3000.0,
                    "remainder": 3000.0
                  },
                  {
                    "date": "2025-05-01",
                    "id": 5,
                    "income_sum": 7000.0,
                    "outcome_sum": 3500.0,
                    "remainder": 3500.0
                  },
                  {
                    "date": "2025-06-01",
                    "id": 6,
                    "income_sum": 8000.0,
                    "outcome_sum": 4000.0,
                    "remainder": 4000.0
                  },
                  {
                    "date": "2025-07-01",
                    "id": 7,
                    "income_sum": 9000.0,
                    "outcome_sum": 4500.0,
                    "remainder": 4500.0
                  },
                  {
                    "date": "2025-08-01",
                    "id": 8,
                    "income_sum": 10000.0,
                    "outcome_sum": 5000.0,
                    "remainder": 5000.0
                  },
                  {
                    "date": "2025-09-01",
                    "id": 9,
                    "income_sum": 11000.0,
                    "outcome_sum": 5500.0,
                    "remainder": 5500.0
                  },
                  {
                    "date": "2025-10-01",
                    "id": 10,
                    "income_sum": 12000.0,
                    "outcome_sum": 6000.0,
                    "remainder": 6000.0
                  }
                ]
                """);
    }

    @GetMapping("/modelE/getIncomeByDirection/{projectName}")
    public ResponseEntity<Object> getIncomeByDirection(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month) {
        Object incomeByDirection = graphicsService.getIncomeByDirection(projectName, year, month);
        return ResponseEntity.ok(incomeByDirection);
    }

    @GetMapping("/modelE/getProfitByProject/{projectName}")
    public ResponseEntity<Object> getProfitByProject(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        Object profitByProject = graphicsService.getProfitByProject(projectName, year, month, unitIds);
        return ResponseEntity.ok(profitByProject);
    }

    @GetMapping("/modelE/getSalesByProject/{projectName}")
    public ResponseEntity<Object> getSalesByProject(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        Object salesByProject = graphicsService.getSalesByProject(projectName, year, month, unitIds);
        return ResponseEntity.ok(salesByProject);
    }

    @GetMapping("/modelE/getRemainers/{projectName}")
    public ResponseEntity<Object> getRemainers(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        Object remainers = graphicsService.getRemainers(projectName, year, month, unitIds);
        return ResponseEntity.ok(remainers);
    }

    @GetMapping("/modelE/getIncomeByCategory/{projectName}")
    public ResponseEntity<Object> getIncomeByCategory(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        Object incomeByCategory = graphicsService.getIncomeByCategory(projectName, year, month, unitIds);
        return ResponseEntity.ok(incomeByCategory);
    }

    @GetMapping("/modelE/getExpensesByCategory/{projectName}")
    public ResponseEntity<Object> getExpensesByCategory(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) List<Long> unitIds) {
        Object expensesByCategory = graphicsService.getExpensesByCategory(projectName, year, month, unitIds);
        return ResponseEntity.ok(expensesByCategory);
    }

    @GetMapping("/modelE/getIncomeByDate/{projectName}")
    public ResponseEntity<Object> getIncomeByDate(
            @PathVariable String projectName,
            @RequestParam int year,
            @RequestParam(required = false) List<Long> unitIds) {
        Object incomeByDate = graphicsService.getIncomeByDate(projectName, year, unitIds);
        return ResponseEntity.ok(incomeByDate);
    }
}
