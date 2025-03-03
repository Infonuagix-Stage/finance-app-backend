package com.example.backend.presentation.category;

public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String creationDate;
    private String type;  // Utilisez String pour faciliter la communication via JSON
    private Long user_id;

    public CategoryResponseDTO() {
    }

    // Constructeur avec paramètres
    public CategoryResponseDTO(String name, Long id, String description, String creationDate, String type, Long user_id) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.user_id = user_id;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getUserId() {
        return user_id;
    }
    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }
}
