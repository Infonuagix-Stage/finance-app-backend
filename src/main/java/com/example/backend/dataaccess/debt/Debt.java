package com.example.backend.dataaccess.debt;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID debtId;  // Identifiant UUID unique

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String creditor;  // Nom du créancier

    @Column(nullable = false)
    private Double amountOwed;  // Montant total de la dette

    @Column(nullable = false)
    private Double amountPaid = 0.0;  // Montant déjà remboursé

    @Column(nullable = false)
    private LocalDate dueDate;  // Date d'échéance

    @Column(nullable = false)
    private Double monthlyPayment;  // Paiement mensuel

    @Column(nullable = false)
    private String status = "Pending";  // Statut de la dette

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Date de création

    @PrePersist
    public void prePersist() {
        if (this.debtId == null) {
            this.debtId = UUID.randomUUID();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public UUID getDebtId() {
        return debtId;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreditor() {
        return creditor;
    }

    public Double getAmountOwed() {
        return amountOwed;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setDebtId(UUID debtId) {
        this.debtId = debtId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public void setAmountOwed(Double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
