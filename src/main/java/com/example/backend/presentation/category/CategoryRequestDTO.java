package com.example.backend.presentation.category;

import java.util.UUID;

public class CategoryRequestDTO {
    private String name;
    private String description;
    private String type;  // Enum sous forme de String (EXPENSE, INCOME)

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    // Getters et Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
