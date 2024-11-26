package com.babymonitor.gateway_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final JwtDecoder jwtDecoder;

    public StartupRunner(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Hardcoded JWT-token (kopieer deze vanuit Keycloak)
        String hardcodedJwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3TWFYUXRWcjVoV05SblZfM09KemFtT0ZtTElQV2hsdDNwdWNnV2dFUUUwIn0.eyJleHAiOjE3MzI2MTY2MjcsImlhdCI6MTczMjYxNjMyNywianRpIjoiNDlhZTVhZmYtNmU4Mi00MjkxLWE1OGEtNzVlZTc2YmQ4OTk4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9CYWJ5bW9uaXRvciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0YTRmMjAwNi0zNTIzLTRmZGMtOWZhNS1jZjU4NWVhMTU5NDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJiYWJ5bW9uaXRvci1jbGllbnQtYXBpIiwic2Vzc2lvbl9zdGF0ZSI6IjY4YjkzYWQ4LWQ3M2MtNDExOC1hYjRiLTQ3NDFhZTAyMzQzOCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiaW5zdHJ1Y3RldXIiLCJkZWZhdWx0LXJvbGVzLWJhYnltb25pdG9yIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZWxuZW1lciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImJhYnltb25pdG9yLWNsaWVudC1hcGkiOnsicm9sZXMiOlsiY2xpZW50X2RlZWxuZW1lciIsImNsaWVudF9pbnN0cnVjdGV1ciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiNjhiOTNhZDgtZDczYy00MTE4LWFiNGItNDc0MWFlMDIzNDM4IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ6YWthcmlhIiwiZW1haWwiOiJ6YWthcmlhQGV4YW1wbGUuY29tIn0.NkiJyVzBXev_ZZJmIvhoKysip_zMwUc40nAFmofT1exVTKJ_tcSnjIwFFjCliQtQsWAfvg-3-qCK1EJW6wWh3qOvGSkto5IPPpeky89oaW7wrlhN3qSuV2W16LvJ0QZO-BcQkQUfHLtnUn4clTipyEqtWcaw2ygrmSjB4tCJ_a77KVkNFObW1FKHOL0Ma1YV7E5hib3GZI4mAmh9iIkwM9vUarxSQB8OGSUCIUYL6gN0SrUZW5m2B7f78M_J0hLqRyY2K5QM_a4KYvDmHokGct84OxcB47-ecNWuWQNW7QBl-3rgFKBHzbR70gTva1eRHPw8eAD3smyCrBduRYOyHw";

        try {
            // Decode de JWT
            Jwt decodedJwt = jwtDecoder.decode(hardcodedJwt);

            // Haal rollen op (bijvoorbeeld uit 'realm_access')
            String roles = decodedJwt.getClaim("realm_access").toString();
            System.out.println("Gebruikersrollen: " + roles);

        } catch (Exception e) {
            System.err.println("Fout bij decoderen van JWT: " + e.getMessage());
        }
    }
}

