package com.example.backend.presentation.user;

import java.util.UUID;

public class UserResponseDTO {

    private UUID userId;
    private String name;
    private String email;

    // Getters and Setters
    public UUID getId() {
        return userId;
    }

    public void setId(UUID userId) {
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
}
