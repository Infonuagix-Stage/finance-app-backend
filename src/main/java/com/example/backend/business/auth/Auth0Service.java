package com.example.backend.business.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0Service {

    @Value("${AUTH0_DOMAIN}")
    private String domain;

    @Value("${AUTH0_M2M_CLIENT_ID}")
    private String m2mClientId;

    @Value("${AUTH0_M2M_CLIENT_SECRET}")
    private String m2mClientSecret;

    @Value("${AUTH0_AUDIENCE}")
    private String audience;

    private String managementToken;

    public void updateUserCredentials(String auth0UserId, String newEmail, String newPassword, String newName) {
        if (managementToken == null) {
            managementToken = fetchManagementToken();
        }
        try {
            patchUser(auth0UserId, newEmail, newPassword, newName);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erreur lors de la mise à jour Auth0 : " + e.getMessage(), e);
        }
    }

    private void patchUser(String auth0UserId, String newEmail, String newPassword, String newName)
            throws IOException, InterruptedException {
        Map<String, Object> body = new HashMap<>();
        if (newEmail != null && !newEmail.isEmpty()) {
            body.put("email", newEmail);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            body.put("password", newPassword);
        }
        if (newName != null && !newName.isEmpty()) {
            // Selon la configuration Auth0, ce champ peut être "name" ou "nickname"
            body.put("username", newName);
            body.put("nickname", newName);
        }

        // Encoder l'identifiant Auth0 pour qu'il soit URL-safe (le caractère | devient %7C, etc.)
        String encodedUserId = URLEncoder.encode(auth0UserId, StandardCharsets.UTF_8);
        String url = "https://" + domain + "/api/v2/users/" + encodedUserId;

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + managementToken)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            throw new IOException("Erreur " + response.statusCode() + " : " + response.body());
        }
    }

    private String fetchManagementToken() {
        try {
            String url = "https://" + domain + "/oauth/token";
            Map<String, String> body = new HashMap<>();
            body.put("client_id", m2mClientId);
            body.put("client_secret", m2mClientSecret);
            body.put("audience", audience);
            body.put("grant_type", "client_credentials");

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new IOException("Impossible de récupérer le token : " + response.body());
            }
            JsonNode jsonNode = mapper.readTree(response.body());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du token : " + e.getMessage(), e);
        }
    }
}
