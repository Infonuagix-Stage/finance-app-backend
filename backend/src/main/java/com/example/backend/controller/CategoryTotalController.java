package com.example.backend.controller;

import com.example.backend.service.ExpenseService;
import com.example.backend.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories/{categoryId}")
public class CategoryTotalController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalForCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
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
