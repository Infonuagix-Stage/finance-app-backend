package com.example.backend.presentation.category;

import com.example.backend.business.transaction.ExpenseService;
import com.example.backend.business.transaction.IncomeService;
import com.example.backend.dataaccess.transaction.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{auth0UserId:.+}/categories/{categoryId}")
public class CategoryTotalController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;


    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalForCategory(
            @PathVariable String auth0UserId,
            @PathVariable UUID categoryId,
            @RequestParam String type,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        BigDecimal total = BigDecimal.ZERO;

        if ("INCOME".equalsIgnoreCase(type)) {
            total = incomeService.getTotalForCategory(auth0UserId, categoryId, year, month);
        } else if ("EXPENSE".equalsIgnoreCase(type)) {
            total = expenseService.getTotalForCategory(auth0UserId, categoryId, year, month);
        }

        return ResponseEntity.ok(total);
    }
}