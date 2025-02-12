package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ProjectRequestDTO {
    private String name;
    private Double targetAmount;
    private Double savedAmount;

    // If you are receiving the date as a string, you can format it:
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private String priority;
    private Double monthlyContribution;

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
