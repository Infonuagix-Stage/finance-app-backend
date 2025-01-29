package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecretKey SECRET_KEY;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

        // Charger la clé secrète depuis le fichier .env
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String secretKeyString = dotenv.get("SECRET_KEY", "0a/+epsW+AKgK9Xuc0LAgZI9LnpGcL8b+eY1C7udlQI=");

        // Vérifier si la clé secrète est valide (>= 256 bits)
        if (Base64.getDecoder().decode(secretKeyString).length < 32) {
            throw new IllegalArgumentException("🚨 La clé secrète doit être d'au moins 256 bits ! Modifie ton .env.");
        }

        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
    }

    @Transactional
    public String register(User user) {
        // Vérifier si l'email existe déjà
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("🚨 Cet email est déjà utilisé !");
        }

        // Encoder le mot de passe et enregistrer l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return generateToken(user.getEmail());
    }

    @Transactional
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(email);
        }
        throw new RuntimeException("❌ Email ou mot de passe incorrect.");
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire en 1h
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
