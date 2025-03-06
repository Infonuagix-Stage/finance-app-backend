package com.example.backend.business.debt;

import com.example.backend.presentation.debt.DebtResponseDTO;
import com.example.backend.dataaccess.debt.Debt;
import com.example.backend.dataaccess.debt.DebtRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DebtService {

    private final DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    /**
     * Récupère toutes les dettes d'un utilisateur.
     *
     * @param userId ID de l'utilisateur.
     * @return Liste des dettes de l'utilisateur.
     */
    public List<Debt> getAllDebtsByUser_UserId(UUID userId) {
        return debtRepository.findByUserId(userId);
    }

    /**
     * Récupère une dette par son ID.
     *
     * @param id ID de la dette.
     * @return La dette correspondante, ou un Optional vide si non trouvée.
     */
    public Optional<Debt> getDebtById(UUID id) {
        return debtRepository.findById(id);
    }

    /**
     * Crée une nouvelle dette.
     *
     * @param debt La dette à créer.
     * @return La dette créée.
     */
    public Debt createDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    /**
     * Met à jour une dette existante.
     *
     * @param debtId          ID de la dette à mettre à jour.
     * @param updatedDebt Les nouvelles données de la dette.
     * @return La dette mise à jour.
     * @throws RuntimeException Si la dette n'est pas trouvée.
     */
    public Debt updateDebt(UUID debtId, Debt updatedDebt) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new RuntimeException("Debt not found with id " + debtId));

        debt.setCreditor(updatedDebt.getCreditor());
        debt.setAmountOwed(updatedDebt.getAmountOwed());
        debt.setAmountPaid(updatedDebt.getAmountPaid());
        debt.setDueDate(updatedDebt.getDueDate());
        debt.setMonthlyPayment(updatedDebt.getMonthlyPayment());
        debt.setStatus(updatedDebt.getStatus());

        return debtRepository.save(debt);
    }

    /**
     * Supprime une dette par son ID.
     *
     * @param debtId ID de la dette à supprimer.
     */
    public void deleteDebt(UUID debtId) {
        debtRepository.deleteByDebtId(debtId);
    }

    /**
     * Convertit une entité Debt en DebtResponseDTO.
     *
     * @param debt L'entité Debt à convertir.
     * @return Le DTO correspondant.
     */
    public DebtResponseDTO convertToResponseDTO(Debt debt) {
        DebtResponseDTO dto = new DebtResponseDTO();

        dto.setDebtId(debt.getDebtId());
        dto.setUserId(debt.getUserId());
        dto.setCreditor(debt.getCreditor());
        dto.setAmountOwed(debt.getAmountOwed());
        dto.setAmountPaid(debt.getAmountPaid());
        dto.setDueDate(debt.getDueDate());
        dto.setMonthlyPayment(debt.getMonthlyPayment());
        dto.setStatus(debt.getStatus());
        dto.setCreatedAt(debt.getCreatedAt());

        return dto;
    }

    /**
     * Récupère toutes les dettes d'un utilisateur sous forme de DTO.
     *
     * @param userId ID de l'utilisateur.
     * @return Liste des DebtResponseDTO.
     */
    public List<DebtResponseDTO> getAllDebtDTOsByUserId(UUID userId) {
        List<Debt> debts = debtRepository.findByUserId(userId);
        return debts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une dette par son ID et l'ID de l'utilisateur.
     *
     * @param debtId     ID de la dette.
     * @param userId ID de l'utilisateur.
     * @return La dette correspondante, ou un Optional vide si non trouvée.
     */
    public Optional<Debt> findByIdAndUserId(UUID debtId, UUID userId) {
        return debtRepository.findByDebtIdAndUserId(debtId, userId);
    }

}