package com.example.backend.dataaccess.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client") // Assurez-vous que le nom de la table correspond bien à la base de données
public class User {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID userId; // UUID unique

    @Column(unique = true)
    private String auth0UserId;

    private String name;
    private String email;
    private String password;


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();



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

    public User(String auth0UserId, String name, String email, String password) {
        this.auth0UserId = auth0UserId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // getters et setters
    public String getAuth0UserId() {
        return auth0UserId;
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

    public void setAuth0UserId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }
}
