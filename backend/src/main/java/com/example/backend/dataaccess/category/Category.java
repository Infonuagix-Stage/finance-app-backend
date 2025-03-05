package com.example.backend.dataaccess.category;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID categoryId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type; // Enum : EXPENSE, INCOME

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public UUID getCategoryId() {
        return categoryId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
    // Génère un UUID, hibernate le fait pas automatiquement
    @PrePersist
    public void generateCategoryId() {
        if (this.categoryId == null) {
            this.categoryId = UUID.randomUUID();
        }
        if (this.creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
    }
}
