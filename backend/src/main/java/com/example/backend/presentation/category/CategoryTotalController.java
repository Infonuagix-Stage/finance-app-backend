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
@RequestMapping("/api/v1/users/{userId}/categories/{categoryId}")
public class CategoryTotalController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private IncomeService incomeService;


    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalForCategory(
            @PathVariable UUID userId,
            @PathVariable UUID categoryId,
            @RequestParam String type) {
        BigDecimal total;
        if ("INCOME".equalsIgnoreCase(type)) {
            total = incomeService.getTotalForCategory(userId, categoryId);
        } else if ("EXPENSE".equalsIgnoreCase(type)) {
            total = expenseService.getTotalForCategory(userId, categoryId);
        } else {
            total = BigDecimal.ZERO;
        }
        return ResponseEntity.ok(total);
    }
}