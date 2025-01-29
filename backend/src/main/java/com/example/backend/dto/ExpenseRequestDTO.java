package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO {
    private BigDecimal montant;
    private LocalDate expenseDate;
    private String description;
    private Integer categoryId; // ID of the associated category
    private Integer userId;     // ID of the associated user

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
