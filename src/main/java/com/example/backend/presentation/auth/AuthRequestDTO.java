package com.example.backend.presentation.auth;

public class AuthRequestDTO {
    private String auth0UserId;
    private String email;
    private String name;

    public AuthRequestDTO() {}

    public AuthRequestDTO(String auth0UserId, String email, String name) {
        this.auth0UserId = auth0UserId;
        this.email = email;
        this.name = name;
    }

    public String getAuth0UserId() {
        return auth0UserId;
    }

    public void setAuth0UserId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
