package com.example.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // Ignore si le fichier .env est absent
                .load();
        String frontEndUrl = System.getenv("FRONTEND_URL");
        if (frontEndUrl == null || frontEndUrl.isEmpty()) {
            frontEndUrl = dotenv.get("FRONTEND_URL"); // Valeur par défaut facultative
        }
        String finalFrontEndUrl = frontEndUrl;
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Permet toutes les routes
                        .allowedOrigins(finalFrontEndUrl)  // Remplace par l'URL de ton frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");  // Autoriser tous les headers
            }
        };
    }
}
