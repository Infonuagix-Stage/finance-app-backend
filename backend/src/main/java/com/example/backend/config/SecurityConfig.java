package com.example.backend.config;

import com.example.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Autoriser l'origine de votre front-end
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Autoriser les méthodes HTTP que vous utilisez
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Autoriser tous les en-têtes ou spécifiez ceux dont vous avez besoin
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Si besoin d'autoriser les credentials
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Appliquer cette configuration à tous les endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/*/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/*/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/*/projects/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/*/projects/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*/projects/**").permitAll()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
