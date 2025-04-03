package com.example.backend.presentation.debt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DebtResponseDTO {

    private Long id;
    private UUID debtId;
    private String auth0UserId;

    private String creditor;
    private Double amountOwed;
    private Double amountPaid;
    private LocalDate dueDate;
    private Double monthlyPayment;
    private String status;
    private LocalDateTime createdAt;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getDebtId() { return debtId; }
    public void setDebtId(UUID debtId) { this.debtId = debtId; }

    public String getAuth0UserId() { return auth0UserId; }
    public void setAuth0UserId(String auth0UserId) { this.auth0UserId = auth0UserId; }

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
