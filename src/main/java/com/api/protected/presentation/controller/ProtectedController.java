package com.api.security.presentation.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@Path("/protected")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Protected Resources", description = "JWT Protected endpoints")
public class ProtectedController {

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/user")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Get user information (requires USER or ADMIN role)")
    @SecurityRequirement(name = "JWT")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, authenticated user!");
        response.put("username", jwt.getName());
        response.put("email", jwt.getClaim("email"));
        response.put("groups", jwt.getGroups());
        response.put("issuer", jwt.getIssuer());
        response.put("audience", jwt.getAudience());
        response.put("expirationTime", jwt.getExpirationTime());
        return response;
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @Operation(summary = "Get admin information (requires ADMIN role)")
    @SecurityRequirement(name = "JWT")
    public Map<String, Object> getAdminInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, admin user!");
        response.put("username", jwt.getName());
        response.put("email", jwt.getClaim("email"));
        response.put("groups", jwt.getGroups());
        response.put("isAdmin", securityContext.isUserInRole("admin"));
        response.put("allClaims", jwt.getClaimNames());
        return response;
    }

    @GET
    @Path("/public")
    @Operation(summary = "Public endpoint (no authentication required)")
    public Map<String, Object> getPublicInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a public endpoint, no authentication required!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GET
    @Path("/profile")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Get user profile (requires authentication)")
    @SecurityRequirement(name = "JWT")
    public Map<String, Object> getUserProfile() {
        Map<String, Object> response = new HashMap<>();
        response.put("username", jwt.getName());
        response.put("email", jwt.getClaim("email"));
        response.put("roles", jwt.getGroups());
        response.put("subject", jwt.getSubject());
        response.put("tokenId", jwt.getTokenID());
        
        // Custom business logic based on roles
        if (securityContext.isUserInRole("admin")) {
            response.put("accessLevel", "FULL_ACCESS");
            response.put("permissions", new String[]{"READ", "WRITE", "DELETE", "ADMIN"});
        } else {
            response.put("accessLevel", "LIMITED_ACCESS");
            response.put("permissions", new String[]{"READ", "WRITE"});
        }
        
        return response;
    }
}
