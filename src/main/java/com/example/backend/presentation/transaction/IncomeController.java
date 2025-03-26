package com.example.backend.presentation.transaction;

import com.example.backend.business.transaction.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{auth0UserId:.+}/categories/{categoryId}/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getMonthlyIncomes(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        List<IncomeResponseDTO> incomes = incomeService.getMonthlyIncomes(auth0UserId, year, month, categoryId);
        return ResponseEntity.ok(incomes);
    }

//    @GetMapping
//    public ResponseEntity<List<IncomeResponseDTO>> getAllIncomes(
//            @PathVariable("auth0UserId") String auth0UserId,
//            @PathVariable("categoryId") UUID categoryId) {
//        List<IncomeResponseDTO> incomes = incomeService.getAllIncomes(auth0UserId, categoryId);
//        return ResponseEntity.ok(incomes);
//    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDTO> getIncomeByIncomeId(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("incomeId") String incomeIdStr
    ) {
        UUID incomeId = UUID.fromString(incomeIdStr);
        IncomeResponseDTO income = incomeService.getIncomeByIncomeId(auth0UserId, categoryId, incomeId);
        return ResponseEntity.ok(income);
    }

    @PostMapping
    public ResponseEntity<IncomeResponseDTO> createIncome(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody IncomeRequestDTO incomeRequestDTO
    ) {
        IncomeResponseDTO createdIncome = incomeService.createIncome(auth0UserId, categoryId, incomeRequestDTO);
        return ResponseEntity.ok(createdIncome);
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDTO> updateIncome(
            @PathVariable("auth0UserId") String auth0UserId,
            @PathVariable("categoryId") UUID categoryId,
            @PathVariable("incomeId") String incomeIdStr,
            @RequestBody IncomeRequestDTO incomeRequestDTO
    ) {
        UUID incomeId = UUID.fromString(incomeIdStr);
        IncomeResponseDTO updatedIncome = incomeService.updateIncome(auth0UserId, categoryId, incomeId, incomeRequestDTO);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable UUID incomeId) {
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.noContent().build();
    }

}
