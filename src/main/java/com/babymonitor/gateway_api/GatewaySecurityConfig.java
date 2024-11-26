package com.babymonitor.gateway_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewaySecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewaySecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf()
                .disable() // CSRF volledig uitschakelen
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll() // OPTIONS-verzoeken toestaan
                .pathMatchers("/identity/login").permitAll() // Sta toegang toe tot /identity/login
                .anyExchange().permitAll() // Toestaan van alle andere requests
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .oauth2ResourceServer()
                .jwt(); // Configureer JWT Resource Server

        http.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.FIRST); // Plaats hem bovenaan de filter chain


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:4173"); // Allow frontend origin
        corsConfig.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
        corsConfig.addAllowedHeader("*"); // Allow all headers
        corsConfig.setAllowCredentials(true); // Allow cookies (optional)

        // Voeg cache-gerelateerde headers toe
        corsConfig.addExposedHeader("Cache-Control");
        corsConfig.addExposedHeader("Pragma");
        corsConfig.addExposedHeader("Expires");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Apply CORS configuration to all paths

        return source;
    }

}
