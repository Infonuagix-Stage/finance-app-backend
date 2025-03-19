package com.example.backend.presentation.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class IncomeRequestDTO {
    private BigDecimal amount;
    private LocalDate incomeDate;
    private String description;
    private UUID categoryId;
    private String auth0UserId;

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
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
    public String getAuth0UserId() {
        return auth0UserId;
    }
    public void setAuth0UserId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }
}
