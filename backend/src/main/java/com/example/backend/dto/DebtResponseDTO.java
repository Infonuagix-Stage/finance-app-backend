package com.example.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DebtResponseDTO {
    private Long id; // ID de la dette
    private Long userId; // ID de l'utilisateur associé
    private String creditor; // Nom du créancier
    private Double amountOwed; // Montant total de la dette
    private Double amountPaid; // Montant déjà remboursé
    private LocalDate dueDate; // Date d'échéance
    private Double monthlyPayment; // Montant à payer chaque mois
    private String status; // Statut de la dette
    private LocalDateTime createdAt; // Date de création

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCreditor() { return creditor; }
    public void setCreditor(String creditor) { this.creditor = creditor; }

    public Double getAmountOwed() { return amountOwed; }
    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }

    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Double getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(Double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}