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

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable UUID id) {
        ExpenseResponseDTO expense = expenseService.getExpenseById(id);
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

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable UUID id,
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        System.out.println(expenseRequestDTO.getAmount());
        System.out.println("ID reçu : " + id);
        ExpenseResponseDTO updatedExpense = expenseService.updateExpense(id, expenseRequestDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
