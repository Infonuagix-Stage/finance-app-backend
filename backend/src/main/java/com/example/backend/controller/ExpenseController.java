package com.example.backend.controller;

import com.example.backend.dto.ExpenseRequestDTO;
import com.example.backend.dto.ExpenseResponseDTO;
import com.example.backend.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories/{categoryId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses(
            @PathVariable("userId") Long userId,
            @PathVariable("categoryId") Long categoryId) {
        List<ExpenseResponseDTO> expenses = expenseService.getAllExpenses(userId, categoryId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {
        ExpenseResponseDTO expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO createdExpense = expenseService.createExpense(expenseRequestDTO);
        return ResponseEntity.ok(createdExpense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        System.out.println(expenseRequestDTO.getAmount());
        System.out.println("ID reçu : " + id);
        ExpenseResponseDTO updatedExpense = expenseService.updateExpense(id, expenseRequestDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
//    @GetMapping("/total")
//    public ResponseEntity<BigDecimal> getTotalForCategory(
//            @PathVariable("userId") Long userId,
//            @PathVariable("categoryId") Long categoryId) {
//        BigDecimal total = expenseService.getTotalForCategory(userId, categoryId);
//        return ResponseEntity.ok(total);
//    }
}
