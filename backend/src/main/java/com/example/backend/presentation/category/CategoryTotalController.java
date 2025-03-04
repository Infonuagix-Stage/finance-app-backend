package com.example.backend.presentation.category;

import com.example.backend.business.transaction.TransactionService;
import com.example.backend.dataaccess.transaction.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories/{categoryId}")
public class CategoryTotalController {

    private final TransactionService transactionService;

    public CategoryTotalController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalForCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestParam("type") TransactionType type) {

        BigDecimal total = transactionService.getTotalForCategory(userId, categoryId, type);
        return ResponseEntity.ok(total);
    }
}
