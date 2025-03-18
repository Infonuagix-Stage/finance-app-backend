package com.example.backend.presentation.auth;

import com.example.backend.dataaccess.user.User;
import com.example.backend.business.auth.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<User> loginOrRegister(@RequestBody AuthRequestDTO authRequest) {
        User user = userRepository.findByAuth0UserId(authRequest.getAuth0UserId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuth0UserId(authRequest.getAuth0UserId());
                    newUser.setName(authRequest.getName());
                    newUser.setEmail(authRequest.getEmail());
                    return userRepository.save(newUser);
                });
        return ResponseEntity.ok(user);
    }
}

