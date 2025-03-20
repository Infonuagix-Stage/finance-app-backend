package com.example.backend.presentation.auth;

public class UpdateUserDTO {
    private String auth0UserId;
    private String email;
    private String name;
    private String password; // Nouveau champ pour le mot de passe

    public UpdateUserDTO() {}

    public UpdateUserDTO(String auth0UserId, String email, String name, String password) {
        this.auth0UserId = auth0UserId;
        this.email = email;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
