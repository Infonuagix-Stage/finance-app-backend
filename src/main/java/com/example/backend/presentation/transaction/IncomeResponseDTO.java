package com.example.backend.presentation.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class IncomeResponseDTO {
    private UUID incomeId;
    private BigDecimal amount;
    private LocalDate incomeDate;
    private String description;
    private String categoryName; // Nom de la catégorie associée
    private String userName;     // Nom de l'utilisateur associé
    private LocalDateTime creationDate;

    // Getters et Setters
    public UUID getIncomeId() {
        return incomeId;
    }
    public void setIncomeId(UUID id) {
        this.incomeId = incomeId;
    }
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
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
