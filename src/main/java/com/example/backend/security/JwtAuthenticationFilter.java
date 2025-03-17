//package com.example.backend.security;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.Base64;
//import java.util.Collections;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final SecretKey SECRET_KEY;
//
//
//    public JwtAuthenticationFilter() {
//        // Charger la clé secrète depuis les variables d'environnement ou .env
//        Dotenv dotenv = Dotenv.configure()
//                .ignoreIfMissing() // Ignore si le fichier .env est absent
//                .load();
//        String secretKeyString = System.getenv("SECRET_KEY");
//        if (secretKeyString == null || secretKeyString.isEmpty()) {
//            secretKeyString = dotenv.get("SECRET_KEY", "defaultKey"); // Valeur par défaut facultative
//        }
//
//        // Valider la clé (256 bits minimum pour HMAC-SHA256)
//        if (Base64.getDecoder().decode(secretKeyString).length < 32) {
//            throw new IllegalStateException("🚨 SECRET_KEY must be at least 256 bits. Update your environment variables or .env file!");
//        }
//        this.SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        // Autoriser les requêtes OPTIONS pour les prérequis CORS
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            System.out.println("🔴 Aucun token reçu ou format incorrect !");
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = authHeader.substring(7);
//        Claims claims;
//
//        try {
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            System.out.println("✅ Token JWT reçu et décodé : " + claims.getSubject());
//        } catch (Exception e) {
//            System.out.println("❌ Token invalide ! Erreur : " + e.getMessage());
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT invalide.");
//            return;
//        }
//
//        String email = claims.getSubject();
//
//        if (email != null) {
//            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(email, "", Collections.emptyList());
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            System.out.println("🔓 Utilisateur authentifié avec succès : " + email);
//        }
//
//        chain.doFilter(request, response);
//    }
//
//}
