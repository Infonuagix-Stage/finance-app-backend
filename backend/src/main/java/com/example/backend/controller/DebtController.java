package com.example.backend.controller;

import com.example.backend.dto.DebtRequestDTO;
import com.example.backend.dto.DebtResponseDTO;
import com.example.backend.model.Debt;
import com.example.backend.service.DebtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/{userId}/debts")
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping
    public ResponseEntity<List<DebtResponseDTO>> getAllDebtsByUserId(@PathVariable Long userId) {
        List<Debt> debts = debtService.getAllDebtsByUserId(userId);
        List<DebtResponseDTO> responseDTOs = debts.stream()
                .map(debtService::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping
    public ResponseEntity<DebtResponseDTO> createDebt(@PathVariable Long userId, @RequestBody DebtRequestDTO debtRequestDTO) {
        Debt debt = new Debt();
        debt.setUserId(userId);
        debt.setCreditor(debtRequestDTO.getCreditor());
        debt.setAmountOwed(debtRequestDTO.getAmountOwed());
        debt.setAmountPaid(debtRequestDTO.getAmountPaid());
        debt.setDueDate(debtRequestDTO.getDueDate());
        debt.setMonthlyPayment(debtRequestDTO.getMonthlyPayment());
        debt.setStatus(debtRequestDTO.getStatus());

        Debt createdDebt = debtService.createDebt(debt);
        DebtResponseDTO responseDTO = debtService.convertToResponseDTO(createdDebt);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DebtResponseDTO> updateDebt(@PathVariable Long userId, @PathVariable Long id, @RequestBody DebtRequestDTO debtRequestDTO) {
        Debt updatedDebt = new Debt();
        updatedDebt.setCreditor(debtRequestDTO.getCreditor());
        updatedDebt.setAmountOwed(debtRequestDTO.getAmountOwed());
        updatedDebt.setAmountPaid(debtRequestDTO.getAmountPaid());
        updatedDebt.setDueDate(debtRequestDTO.getDueDate());
        updatedDebt.setMonthlyPayment(debtRequestDTO.getMonthlyPayment());
        updatedDebt.setStatus(debtRequestDTO.getStatus());

        Debt debt = debtService.updateDebt(id, updatedDebt);
        DebtResponseDTO responseDTO = debtService.convertToResponseDTO(debt);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(@PathVariable Long userId, @PathVariable Long id) {
        debtService.deleteDebt(id);
        return ResponseEntity.noContent().build();
    }
}