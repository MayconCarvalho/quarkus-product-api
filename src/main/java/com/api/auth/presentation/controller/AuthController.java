package com.api.auth.presentation.controller;

import com.api.auth.application.dto.AuthResponse;
import com.api.auth.application.dto.LoginRequest;
import com.api.auth.application.dto.RegisterRequest;
import com.api.auth.application.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @Operation(summary = "Register a new user")
    @APIResponse(
        responseCode = "201", 
        description = "User registered successfully",
        content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @APIResponse(responseCode = "400", description = "Bad request - username or email already exists")
    public Response register(@Valid RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/login")
    @Operation(summary = "Login user")
    @APIResponse(
        responseCode = "200", 
        description = "User logged in successfully",
        content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @APIResponse(responseCode = "401", description = "Unauthorized - invalid credentials")
    public Response login(@Valid LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return Response.ok(response).build();
        } catch (NotAuthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Invalid credentials")).build();
        }
    }

    // Error response class
    public static class ErrorResponse {
        private String message;

        public ErrorResponse() {}

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
