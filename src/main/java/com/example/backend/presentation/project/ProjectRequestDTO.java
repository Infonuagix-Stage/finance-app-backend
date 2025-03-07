package com.example.backend.presentation.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ProjectRequestDTO {
    @NotNull(message = "Le nom du projet ne peut pas être vide")
    private String name;
    @NotNull(message = "Le montant cible ne peut pas être vide")
    private Double targetAmount;


    private Double savedAmount;

    @NotNull(message = "La priorité ne peut pas être vide")
    private String priority;

    private Double monthlyContribution;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date limite ne peut pas être vide")
    private LocalDate deadline;
    // Getters and setters
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
}
