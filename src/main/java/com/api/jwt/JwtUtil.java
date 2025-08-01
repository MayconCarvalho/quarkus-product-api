package com.api.jwt;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtUtil {

    @ConfigProperty(name = "smallrye.jwt.new-token.issuer")
    String issuer;

    @ConfigProperty(name = "smallrye.jwt.new-token.audience")
    String audience;

    public String generateUserToken(String username, String email, String... groups) {
        return generateToken(username, email, Duration.ofHours(1), groups);
    }

    public String generateAdminToken(String username, String email) {
        return generateToken(username, email, Duration.ofHours(8), "admin", "user");
    }

    private String generateToken(String username, String email, Duration duration, String... groups) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        Set<String> groupSet = new HashSet<>(Arrays.asList(groups));
        
        return claimsBuilder
                .issuer(issuer)
                .audience(audience)
                .subject(username)
                .claim("email", email)
                .groups(groupSet)
                .expiresIn(duration)
                .sign();
    }
}
