package com.example.backend.presentation.user;

import java.util.UUID;

public class UserResponseDTO {

    private String auth0UserId;
    private String name;
    private String email;

    // Getters and Setters
    public String getId() {
        return auth0UserId;
    }

    public void setId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
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
