package com.example.backend.dataaccess.transaction;

import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Montant de la dépense
    private BigDecimal amount;

    // Date de la dépense
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    // Description de la dépense
    @Column(columnDefinition = "TEXT")
    private String description;

    // Catégorie associée à cette dépense (doit être de type Category, ou ExpenseCategory si vous utilisez l'héritage)
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Utilisateur qui a réalisé la dépense
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Date de création de l'enregistrement
    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    // Constructeur par défaut
    public Expense() {
    }

    // Constructeur avec paramètres
    public Expense(BigDecimal amount, LocalDate expenseDate, String description, Category category, User user) {
        this.amount = amount;
        this.expenseDate = expenseDate;
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
