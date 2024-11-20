package com.babymonitor.gateway_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable() // CSRF uitschakelen
                .authorizeExchange()
                .pathMatchers("/api/**").hasRole("realm-admin")   // Beperk toegang tot bepaalde paden
                .pathMatchers("/identity/**").authenticated()    // Andere paden moeten geauthenticeerd zijn
                .anyExchange().permitAll()  // Andere paden zijn open
                .and()
                .oauth2ResourceServer()           // Zet OAuth2-resource server in
                .jwt();                          // Gebruik JWT voor autorisatie

        return http.build();
    }

}
