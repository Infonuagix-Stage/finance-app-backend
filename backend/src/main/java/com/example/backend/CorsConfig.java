package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Permet toutes les routes
                        .allowedOrigins("http://localhost:3000")  // Remplace par l'URL de ton frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Méthodes autorisées
                        .allowedHeaders("*");  // Autoriser tous les headers
            }
        };
    }
}
