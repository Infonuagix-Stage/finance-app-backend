package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // ID de l'utilisateur associé à cette dette

    @Column(nullable = false)
    private String creditor; // Nom du créancier (personne ou entité à qui l'argent est dû)

    @Column(nullable = false)
    private Double amountOwed; // Montant total de la dette

    @Column(nullable = false)
    private Double amountPaid = 0.0; // Montant déjà remboursé

    @Column(nullable = false)
    private LocalDate dueDate; // Date d'échéance de la dette

    @Column(nullable = false)
    private Double monthlyPayment; // Montant à payer chaque mois

    @Column(nullable = false)
    private String status = "Pending"; // Statut de la dette (ex: "Pending", "Paid", "Overdue")

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Date de création de la dette

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCreditor() { return creditor; }
    public void setCreditor(String creditor) { this.creditor = creditor; }

    public Double getAmountOwed() { return amountOwed; }
    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }

    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Double getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(Double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}