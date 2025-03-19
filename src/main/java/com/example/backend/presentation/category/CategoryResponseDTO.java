package com.example.backend.presentation.category;

import java.util.UUID;

public class CategoryResponseDTO {
    private UUID categoryId;
    private String name;
    private String description;
    private String creationDate;
    private String type;
    private String auth0UserId;

    public CategoryResponseDTO() {
    }
    public CategoryResponseDTO(UUID categoryId, String name, String description, String creationDate, String type, String auth0UserId) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.auth0UserId = auth0UserId;
    }

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
    public String getAuth0UserId() {
        return auth0UserId;
    }
    public void setAuth0UserId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }
}
