package com.example.backend.presentation.debt;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DebtRequestDTO {

    @NotNull(message = "Le nom du créancier est requis")
    private String creditor;

    @NotNull(message = "Le montant dû est requis")
    private Double amountOwed;

    private Double amountPaid = 0.0;

    @NotNull(message = "La date d'échéance est requise")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message = "Le paiement mensuel est requis")
    private Double monthlyPayment;

    private String status = "Pending"; // Peut être changé depuis le frontend si tu veux

    // Getters & Setters
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
}
