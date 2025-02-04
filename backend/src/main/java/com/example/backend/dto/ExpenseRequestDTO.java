package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO {
    private BigDecimal montant;
    private LocalDate expenseDate;
    private String description;
    private Long categoryId; // ID of the associated category
    private Long userId;     // ID of the associated user

    // Getters and Setters
    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
