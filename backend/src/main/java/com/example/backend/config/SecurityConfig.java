package com.example.backend.config;

import com.example.backend.security.JwtAuthenticationFilter;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        if (frontendUrl == null) {
            Dotenv dotenv = Dotenv.configure().load();
            frontendUrl = dotenv.get("FRONTEND_URL");
        }
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
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
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/*/debts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/*/debts/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*/debts/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*/debts/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*/categories/*/expenses/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*/categories/*/expenses/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*/categories/*/incomes/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*/categories/*/incomes/**").permitAll()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
