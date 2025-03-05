package com.example.backend.presentation.category;

import java.util.UUID;

public class CategoryResponseDTO {
    private UUID categoryId;  // Utilisation d'UUID au lieu de Long
    private String name;
    private String description;
    private String creationDate;
    private String type;  // Utilisé comme String pour JSON
    private UUID userId;  // Utilisation d'UUID au lieu de Long

    public CategoryResponseDTO() {
    }

    // Constructeur avec paramètres
    public CategoryResponseDTO(UUID categoryId, String name, String description, String creationDate, String type, UUID userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.userId = userId;
    }

    // Getters and setters
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
