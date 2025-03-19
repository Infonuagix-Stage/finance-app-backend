//package com.example.backend.presentation.debt;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import java.time.LocalDate;
//
//public class DebtRequestDTO {
//    private String creditor; // Nom du créancier
//    private Double amountOwed; // Montant total de la dette
//    private Double amountPaid; // Montant déjà remboursé
//    private Double monthlyPayment; // Montant à payer chaque mois
//
//    @JsonFormat(pattern = "yyyy-MM-dd") // Format de la date
//    private LocalDate dueDate; // Date d'échéance
//
//    private String status; // Statut de la dette (ex: "Pending", "Paid", "Overdue")
//
//    // Getters and Setters
//    public String getCreditor() { return creditor; }
//    public void setCreditor(String creditor) { this.creditor = creditor; }
//
//    public Double getAmountOwed() { return amountOwed; }
//    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }
//
//    public Double getAmountPaid() { return amountPaid; }
//    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
//
//    public Double getMonthlyPayment() { return monthlyPayment; }
//    public void setMonthlyPayment(Double monthlyPayment) { this.monthlyPayment = monthlyPayment; }
//
//    public LocalDate getDueDate() { return dueDate; }
//    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//}