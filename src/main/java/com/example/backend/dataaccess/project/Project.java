package com.example.backend.dataaccess.project;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation en base
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID projectId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double targetAmount;

    @Column(nullable = false)
    private Double savedAmount = 0.0;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private Double monthlyContribution;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt; // Date de création auto-générée

    @PrePersist
    public void generateProjectId() {
        if (this.projectId == null) {
            this.projectId = UUID.randomUUID();
        }
    }

    //  Getters
    public Long getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public Double getSavedAmount() {
        return savedAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }

    public Double getMonthlyContribution() {
        return monthlyContribution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //  Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setSavedAmount(Double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setMonthlyContribution(Double monthlyContribution) {
        this.monthlyContribution = monthlyContribution;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
