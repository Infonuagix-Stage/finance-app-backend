package com.example.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Montant de l'entrée d'argent
    private BigDecimal amount;

    // Date de l'entrée d'argent
    @Column(name = "income_date")
    private LocalDate entryDate;

    // Description de l'entrée
    @Column(columnDefinition = "TEXT")
    private String description;

    // Catégorie associée à cette entrée (doit être de type Category, ou IncomeCategory si vous utilisez l'héritage)
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Utilisateur concerné par l'entrée
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Date de création de l'enregistrement
    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    // Constructeur par défaut
    public Income() {
    }

    // Constructeur avec paramètres
    public Income(BigDecimal amount, LocalDate entryDate, String description, Category category, User user) {
        this.amount = amount;
        this.entryDate = entryDate;
        this.description = description;
        this.category = category;
        this.user = user;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
