package com.babymonitor.gateway_api.config;

import com.babymonitor.gateway_api.services.JwtAuthenticationFilter;

import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/data/**").permitAll()  // Allow WebSocket connections without auth
                        .pathMatchers("/identity/login").permitAll()
                        .anyExchange().permitAll()
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(List.of("*")); // For development only!
        corsConfig.setAllowedMethods(List.of("*"));
        corsConfig.setAllowedHeaders(List.of(
            "*",
            "Authorization",
            "Content-Type",
            "Accept",
            "Upgrade",
            "Connection",
            "Sec-WebSocket-Key",
            "Sec-WebSocket-Version",
            "Sec-WebSocket-Extensions",
            "Sec-WebSocket-Protocol"
        ));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    // old version
    // @Bean
    // public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {


    //     return http
    //             .csrf(csrf -> csrf.disable())
    //             .authorizeExchange(auth -> auth.anyExchange().permitAll())
    //             .httpBasic(httpBasic -> httpBasic.disable())
    //             .formLogin(formLogin -> formLogin.disable())
    //             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //             .build();

    //     // return http
    //     //         .csrf(csrf -> csrf.disable())
    //     //         .authorizeExchange(auth -> auth
    //     //                 .pathMatchers(HttpMethod.OPTIONS).permitAll()
    //     //                 .pathMatchers(HttpMethod.POST, "/identity/**").permitAll()
    //     //                 .anyExchange().authenticated()
    //     //         )
    //     //         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //     //         .build();
    // }
    
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration corsConfig = new CorsConfiguration();

    //     // Specify allowed origins
    //     corsConfig.addAllowedOrigin("http://localhost:4173");
    //     corsConfig.addAllowedOrigin("ws://localhost:8722");

    //     // Allow only necessary HTTP methods
    //     corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

    //     // Allow only necessary headers
    //     corsConfig.setAllowedHeaders(List.of(
    //         "Authorization", 
    //         "Content-Type", 
    //         "Accept", 
    //         "Upgrade",           // Important for WebSocket
    //         "Connection",        // Important for WebSocket
    //         "Sec-WebSocket-Key",
    //         "Sec-WebSocket-Version",
    //         "Sec-WebSocket-Extensions",
    //         "Sec-WebSocket-Protocol"
    //     ));

    //     // Allow credentials (important for JWT authentication)
    //     corsConfig.setAllowCredentials(true);

    //     // Set max age for preflight requests
    //     corsConfig.setMaxAge(3600L);

    //     corsConfig.setExposedHeaders(List.of(
    //         "Authorization", 
    //         "Content-Type", 
    //         "Connection", 
    //         "Upgrade"
    //     ));

    //     // Apply CORS configuration to all endpoints
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", corsConfig);

    //     return source;
    // }
}