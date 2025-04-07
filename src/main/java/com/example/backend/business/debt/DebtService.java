package com.example.backend.business.debt;

import com.example.backend.dataaccess.debt.Debt;
import com.example.backend.dataaccess.debt.DebtRepository;
import com.example.backend.presentation.debt.DebtResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DebtService {

    private final DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Optional<Debt> getDebtById(UUID debtId) {
        return debtRepository.findByDebtId(debtId);
    }

    public Debt createDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    public Debt save(Debt debt) {
        return debtRepository.save(debt);
    }

    public void deleteDebt(UUID debtId) {
        debtRepository.deleteByDebtId(debtId);
    }

    public Optional<Debt> findByIdAndUserId(UUID debtId, String auth0UserId) {
        return debtRepository.findByDebtIdAndUser_Auth0UserId(debtId, auth0UserId);
    }

    public void deleteByIdAndUserId(UUID debtId, String auth0UserId) {
        debtRepository.findByDebtIdAndUser_Auth0UserId(debtId, auth0UserId)
                .ifPresent(debtRepository::delete);
    }

    public List<Debt> getAllDebtsByUser(String auth0UserId) {
        return debtRepository.findByUser_Auth0UserId(auth0UserId);
    }

    public DebtResponseDTO convertToResponseDTO(Debt debt) {
        DebtResponseDTO dto = new DebtResponseDTO();
        dto.setId(debt.getId());
        dto.setDebtId(debt.getDebtId());
        dto.setCreditor(debt.getCreditor());
        dto.setAmountOwed(debt.getAmountOwed());
        dto.setAmountPaid(debt.getAmountPaid());
        dto.setDueDate(debt.getDueDate());
        dto.setMonthlyPayment(debt.getMonthlyPayment());
        dto.setStatus(debt.getStatus());
        dto.setCreatedAt(debt.getCreatedAt());
        dto.setAuth0UserId(debt.getUser().getAuth0UserId());
        return dto;
    }

    public List<DebtResponseDTO> convertToResponseDTOList(List<Debt> debts) {
        return debts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}
