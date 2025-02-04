package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecretKey SECRET_KEY;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

        // Load the secret key from .env
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); // Load .env file
        String secretKeyString = dotenv.get("SECRET_KEY", "defaultKey");

        // Validate the key (256 bits minimum for HMAC-SHA256)
        if (Base64.getDecoder().decode(secretKeyString).length < 32) {
            throw new IllegalArgumentException("🚨 SECRET_KEY must be at least 256 bits. Update your .env file!");
        }

        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString)); // Create the SecretKey
    }

    @Transactional
    public String register(User user) {
        // Check if the email already exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("🚨 Email is already in use!");
        }

        // Encode the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Sauvegarder l'utilisateur et récupérer l'utilisateur sauvegardé avec son id
        User savedUser = userRepository.save(user);
        return generateToken(savedUser.getEmail(), savedUser.getId());
    }

    @Transactional
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(email, user.get().getId());
        }
        throw new RuntimeException("❌ Incorrect email or password.");
    }

    private String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)  // Ajout de l'ID utilisateur dans le payload du token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expires in 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
