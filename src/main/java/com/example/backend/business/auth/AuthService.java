package com.example.backend.business.auth;

import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecretKey SECRET_KEY;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();


        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String secretKeyString = System.getenv("SECRET_KEY");
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            secretKeyString = dotenv.get("SECRET_KEY", "defaultKey");
        }

        // Valider la clé (256 bits minimum pour HMAC-SHA256)
        if (Base64.getDecoder().decode(secretKeyString).length < 32) {
            throw new IllegalArgumentException("SECRET_KEY must be at least 256 bits. Update your environment variables or .env file!");
        }
        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
    }

    @Transactional
    public Map<String, Object> register(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Email déjà utilisé pour un autre compte."
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        String token = generateToken(savedUser);

        Map<String, Object> response = new HashMap<>();
       // response.put("userId", savedUser.getAuth0UserId()); // UUID de l'utilisateur
        response.put("token", token); // JWT

        return response;
    }


    @Transactional
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(user.get());
        }
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect."
        );
    }


    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("auth0userId", user.getAuth0UserId())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire après 1 heure
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}
