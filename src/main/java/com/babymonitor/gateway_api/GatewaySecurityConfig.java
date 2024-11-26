package com.babymonitor.gateway_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

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
                .pathMatchers("/identity/login").permitAll() // Sta toegang toe tot /identity/login
                .anyExchange().permitAll() // Toestaan van alle andere requests
                .and()
                .oauth2ResourceServer()
                .jwt(); // Configureer JWT Resource Server

        http.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.FIRST); // Plaats hem bovenaan de filter chain


        return http.build();
    }
}
