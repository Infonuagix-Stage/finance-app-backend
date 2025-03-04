//package com.example.backend;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
//        String frontEndUrl = dotenv.get("FRONTEND_URL", "http://localhost:3000");
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")  // Permet toutes les routes
//                        .allowedOrigins(frontEndUrl)  // Remplace par l'URL de ton frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);// Autoriser tous les headers
//            }
//        };
//    }
//}
