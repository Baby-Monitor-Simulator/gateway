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
        String hardcodedJwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3TWFYUXRWcjVoV05SblZfM09KemFtT0ZtTElQV2hsdDNwdWNnV2dFUUUwIn0.eyJleHAiOjE3MzI2MTMxMTYsImlhdCI6MTczMjYxMjgxNiwianRpIjoiOThkYTRlOTAtNjc2MS00ZTNkLTg2YzItZjRmNGY3YTEyOGI0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9CYWJ5bW9uaXRvciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0YTRmMjAwNi0zNTIzLTRmZGMtOWZhNS1jZjU4NWVhMTU5NDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJiYWJ5bW9uaXRvci1jbGllbnQtYXBpIiwic2Vzc2lvbl9zdGF0ZSI6IjlkZjMwMzIwLWRkZWUtNDI0OS04OTBjLWU1ODJhOWE5NTk1NCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiaW5zdHJ1Y3RldXIiLCJkZWZhdWx0LXJvbGVzLWJhYnltb25pdG9yIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZWxuZW1lciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImJhYnltb25pdG9yLWNsaWVudC1hcGkiOnsicm9sZXMiOlsiY2xpZW50X2RlZWxuZW1lciIsImNsaWVudF9pbnN0cnVjdGV1ciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiOWRmMzAzMjAtZGRlZS00MjQ5LTg5MGMtZTU4MmE5YTk1OTU0IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ6YWthcmlhIiwiZW1haWwiOiJ6YWthcmlhQGV4YW1wbGUuY29tIn0.SwQJ3yiVXUa86sp1wU8_XI6lNVwgxG_uvs2UC7BZGCOGQWb1aaCCGc3g1SaeU9HJI6e9oh6NbsWsKS4NZQAydNzSPoLyVWlYw0XouqLjnbKzJy5lujfi6-HnaWMNj_DpynC9oKwIFTotJeweKMIB34uP43j0pgUqOPbPsQJ3KnC0PL21LPoHFpXKuezxSpsR7co63Y_hGo3k0fGhBVIaZsuuRJDVKG3GMKNlX66ozilc6FMHEHPL9f67YfbGgdeanLwJvReYidKVm6uWLSnzRGrjqy5Xb5JdfFJR_9n741gpESbgRexlwYtVZRBjiA2TGNyeJooeWbakRD0ZtcF36g"; // Plaats hier een geldig JWT

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

