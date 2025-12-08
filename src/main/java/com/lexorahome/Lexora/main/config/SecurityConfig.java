package com.lexorahome.Lexora.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api/v3/auth/**").permitAll()
                        .requestMatchers("/api/v3/price-list").permitAll()
                        .requestMatchers("/api/v3/picture/types").permitAll()
                        .requestMatchers("/api/v3/picture/**").permitAll()
                        .requestMatchers("/api/v3/categories").permitAll()
                        .requestMatchers("/api/v3/colors").permitAll()
                        .requestMatchers("/api/v3/goods/**").permitAll()
                        .requestMatchers("/api/v3/lexora-sku-guide").permitAll()
                        .requestMatchers("/management/**").permitAll()
                        .requestMatchers("/api/v3/skuuudle-report/upload").permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // MVC, не reactive!
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
