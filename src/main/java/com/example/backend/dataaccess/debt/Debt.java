package com.example.backend.dataaccess.debt;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private UUID debtId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "auth0UserId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String creditor;

    @Column(nullable = false)
    private Double amountOwed;

    @Column(nullable = false)
    private Double amountPaid = 0.0;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private Double monthlyPayment;

    @Column(nullable = false)
    private String status = "Pending";

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void generateDebtId() {
        if (this.debtId == null) {
            this.debtId = UUID.randomUUID();
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public UUID getDebtId() { return debtId; }
    public User getUser() { return user; }
    public String getCreditor() { return creditor; }
    public Double getAmountOwed() { return amountOwed; }
    public Double getAmountPaid() { return amountPaid; }
    public LocalDate getDueDate() { return dueDate; }
    public Double getMonthlyPayment() { return monthlyPayment; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setDebtId(UUID debtId) { this.debtId = debtId; }
    public void setUser(User user) { this.user = user; }
    public void setCreditor(String creditor) { this.creditor = creditor; }
    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setMonthlyPayment(Double monthlyPayment) { this.monthlyPayment = monthlyPayment; }
    public void setStatus(String status) { this.status = status; }
}
