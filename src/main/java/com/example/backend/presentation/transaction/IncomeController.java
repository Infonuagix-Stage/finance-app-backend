package com.example.backend.presentation.transaction;

import com.example.backend.business.transaction.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories/{categoryId}/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    // GET : Récupérer toutes les incomes pour un utilisateur dans une catégorie donnée
    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getAllIncomes(
            @PathVariable("userId") UUID userId,
            @PathVariable("categoryId") UUID categoryId) {
        List<IncomeResponseDTO> incomes = incomeService.getAllIncomes(userId, categoryId);
        return ResponseEntity.ok(incomes);
    }

    // GET : Récupérer une income par ID
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> getIncomeById(@PathVariable UUID id) {
        IncomeResponseDTO income = incomeService.getIncomeById(id);
        return ResponseEntity.ok(income);
    }

    // POST : Créer une nouvelle income
    @PostMapping
    public ResponseEntity<IncomeResponseDTO> createIncome(
                                                          @RequestBody IncomeRequestDTO incomeRequestDTO) {
        IncomeResponseDTO createdIncome = incomeService.createIncome(incomeRequestDTO);
        return ResponseEntity.ok(createdIncome);
    }

    // PUT : Mettre à jour une income existante
    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> updateIncome(
            @PathVariable UUID id,
            @RequestBody IncomeRequestDTO incomeRequestDTO) {
        IncomeResponseDTO updatedIncome = incomeService.updateIncome(id, incomeRequestDTO);
        return ResponseEntity.ok(updatedIncome);
    }

    // DELETE : Supprimer une income par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable UUID incomeId) {
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.noContent().build();
    }

}
