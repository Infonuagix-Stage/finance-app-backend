package com.example.backend.dataaccess.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client") // Assurez-vous que le nom de la table correspond bien à la base de données
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID userId; // UUID unique

    private String name;
    private String email;
    private String password;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    // ⚠ Génération automatique de `userId` avant insertion
    @PrePersist
    public void generateUserId() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID();
        }
    }

    // Constructeurs
    public User() {
    }

    public User(String name, String email, String password, UUID userId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    // Getters and Setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
