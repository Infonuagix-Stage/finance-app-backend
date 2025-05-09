package com.example.backend.presentation.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProjectResponseDTO {
    private Long id;
    private UUID projectId;
    private String name;
    private Double targetAmount;
    private Double savedAmount;
    private LocalDate deadline;
    private String priority;
    private Double monthlyContribution;
    private String auth0UserId;
    private LocalDateTime createdAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(Double targetAmount) { this.targetAmount = targetAmount; }

    public Double getSavedAmount() { return savedAmount; }
    public void setSavedAmount(Double savedAmount) { this.savedAmount = savedAmount; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Double getMonthlyContribution() { return monthlyContribution; }
    public void setMonthlyContribution(Double monthlyContribution) { this.monthlyContribution = monthlyContribution; }

    public String getAuth0UserId() { return auth0UserId; }
    public void setAuth0UserId(String auth0UserId) { this.auth0UserId = auth0UserId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
