package com.babymonitor.gateway_api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);

            try {
                // Decode the JWT token
                Jwt decodedJwt = jwtDecoder.decode(jwtToken);

                // Extract roles (example: from 'realm_access')
                String roles = decodedJwt.getClaim("realm_access").toString();

                // Optionally, you could store the roles in the SecurityContext, or create an authentication object here.
                // Authentication authentication = new JwtAuthenticationToken(decodedJwt, roles);
                //SecurityContextHolder.getContext().setAuthentication(authentication);
                response.setHeader("roles", roles);
            } catch (JwtException e) {
                // Handle invalid JWT or token validation failure
                response.setStatus(401);
                response.getWriter().write("Invalid JWT token");
                return;
            }
        }

        // Proceed to the next filter or controller
        filterChain.doFilter(request, response);
    }
}
