package com.example.backend.presentation.auth;

import com.example.backend.business.auth.Auth0Service;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Auth0Service auth0Service;

    @PostMapping("/login")
    public ResponseEntity<User> loginOrRegister(@RequestBody AuthRequestDTO authRequest) {
        User user = userRepository.findByAuth0UserId(authRequest.getAuth0UserId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUserId(UUID.randomUUID());
                    newUser.setName(authRequest.getName());
                    newUser.setEmail(authRequest.getEmail());
                    newUser.setAuth0UserId(authRequest.getAuth0UserId());
                    return userRepository.save(newUser);
                });
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UpdateUserDTO updateRequest) {
        // Appel à Auth0 pour mettre à jour email, mot de passe et nom
        auth0Service.updateUserCredentials(
                updateRequest.getAuth0UserId(),
                updateRequest.getEmail(),
                updateRequest.getPassword(),
                updateRequest.getName()
        );

        // Retour d'une réponse
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Mise à jour réussie");
        response.put("email", updateRequest.getEmail());
        response.put("name", updateRequest.getName());
        return ResponseEntity.ok(response);
    }
}
