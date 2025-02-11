package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
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

        // Charger la clé secrète depuis les variables d'environnement ou .env
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // Ignore si le fichier .env est absent
                .load();
        String secretKeyString = System.getenv("SECRET_KEY");
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            secretKeyString = dotenv.get("SECRET_KEY", "defaultKey"); // Valeur par défaut facultative
        }

        // Valider la clé (256 bits minimum pour HMAC-SHA256)
        if (Base64.getDecoder().decode(secretKeyString).length < 32) {
            throw new IllegalArgumentException("🚨 SECRET_KEY must be at least 256 bits. Update your environment variables or .env file!");
        }
        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
    }

    @Transactional
    public String register(User user) {
        // Check if the email already exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            // Encode the password and save the user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Sauvegarder l'utilisateur et récupérer l'utilisateur sauvegardé avec son id
            User savedUser = userRepository.save(user);
            return generateToken(savedUser.getEmail(), savedUser.getId(), savedUser.getName());
        }
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email déjà utilisé pour un autre compte."
        );

    }

    @Transactional
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        // On vérifie que l'utilisateur existe et que le mot de passe correspond
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(email, user.get().getId(), user.get().getName());
        }

        // Au lieu d'un RuntimeException, on renvoie un statut HTTP 401 (UNAUTHORIZED)
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect."
        );
    }


    private String generateToken(String email, Long userId, String name) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)  // Ajout de l'ID utilisateur dans le payload du token
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expires in 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
