//package com.example.backend.presentation.debt;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public class DebtResponseDTO {
//    private UUID debtId; // ID de la dette
//    private UUID userId; // ID de l'utilisateur associé
//    private String creditor; // Nom du créancier
//    private Double amountOwed; // Montant total de la dette
//    private Double amountPaid; // Montant déjà remboursé
//    private LocalDate dueDate; // Date d'échéance
//    private Double monthlyPayment; // Montant à payer chaque mois
//    private String status; // Statut de la dette
//    private LocalDateTime createdAt; // Date de création
//
//    // Getters and Setters
//    public UUID getDebtId() { return debtId; }
//    public void setDebtId(UUID debtId) { this.debtId = debtId; }
//    public UUID getUserId() { return userId; }
//    public void setUserId(UUID userId) { this.userId = userId; }
//
//    public String getCreditor() { return creditor; }
//    public void setCreditor(String creditor) { this.creditor = creditor; }
//
//    public Double getAmountOwed() { return amountOwed; }
//    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }
//
//    public Double getAmountPaid() { return amountPaid; }
//    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
//
//    public LocalDate getDueDate() { return dueDate; }
//    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
//
//    public Double getMonthlyPayment() { return monthlyPayment; }
//    public void setMonthlyPayment(Double monthlyPayment) { this.monthlyPayment = monthlyPayment; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//
//}