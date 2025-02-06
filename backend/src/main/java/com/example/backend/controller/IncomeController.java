package com.example.backend.controller;

import com.example.backend.dto.IncomeRequestDTO;
import com.example.backend.dto.IncomeResponseDTO;
import com.example.backend.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
            @PathVariable("userId") Long userId,
            @PathVariable("categoryId") Long categoryId) {
        List<IncomeResponseDTO> incomes = incomeService.getAllIncomes(userId, categoryId);
        return ResponseEntity.ok(incomes);
    }

    // GET : Récupérer une income par ID
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponseDTO> getIncomeById(@PathVariable Long id) {
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
            @PathVariable Long id,
            @RequestBody IncomeRequestDTO incomeRequestDTO) {
        IncomeResponseDTO updatedIncome = incomeService.updateIncome(id, incomeRequestDTO);
        return ResponseEntity.ok(updatedIncome);
    }

    // DELETE : Supprimer une income par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    // GET : Obtenir le total des incomes pour une catégorie d'un utilisateur
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalForCategory(
            @PathVariable("userId") Long userId,
            @PathVariable("categoryId") Long categoryId) {
        BigDecimal total = incomeService.getTotalForCategory(userId, categoryId);
        return ResponseEntity.ok(total);
    }
}
