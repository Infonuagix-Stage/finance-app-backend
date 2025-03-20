package com.example.backend.presentation.transaction;

import com.example.backend.business.transaction.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{auth0UserId:.+}/categories/{categoryId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId) {
        List<ExpenseResponseDTO> expenses = expenseService.getAllExpenses(auth0UserId, categoryId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable UUID expenseId) {
        ExpenseResponseDTO expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        ExpenseResponseDTO createdExpense = expenseService.createExpense(auth0UserId, categoryId, expenseRequestDTO);
        return ResponseEntity.ok(createdExpense);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("expenseId") String expenseIdStr,
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        UUID expenseId = UUID.fromString(expenseIdStr);
        ExpenseResponseDTO updatedExpense = expenseService.updateExpense(auth0UserId, categoryId, expenseId, expenseRequestDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
