package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeRequestDTO {
    private BigDecimal amount;
    private LocalDate incomeDate;
    private String description;
    private Long categoryId; // ID de la catégorie associée
    private Long userId;     // ID de l'utilisateur associé

    // Getters et Setters
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public LocalDate getIncomeDate() {
        return incomeDate;
    }
    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
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
