package com.example.backend.dataaccess.transaction;

import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID expenseId;  // Identifiant UUID unique

    private BigDecimal amount;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        if (this.expenseId == null) {
            this.expenseId = UUID.randomUUID();
        }
    }
    public Expense() {
    }

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

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setId(UUID expenseId) {
        this.expenseId = expenseId;
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
