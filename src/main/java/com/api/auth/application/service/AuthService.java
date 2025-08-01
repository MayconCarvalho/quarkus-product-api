package com.api.auth.application.service;

import com.api.auth.application.dto.AuthResponse;
import com.api.auth.application.dto.LoginRequest;
import com.api.auth.application.dto.RegisterRequest;
import com.api.auth.domain.User;
import com.api.auth.infrastructure.repository.UserRepository;
import com.api.jwt.JwtUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        user.setRole(User.Role.USER);

        userRepository.persist(user);

        // Generate token
        String token = jwtUtil.generateUserToken(
            user.getUsername(), 
            user.getEmail(), 
            user.getRole().toString().toLowerCase()
        );

        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().toString());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotAuthorizedException("Invalid credentials"));

        if (!verifyPassword(request.getPassword(), user.getPassword())) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        // Generate token based on role
        String token;
        if (user.getRole() == User.Role.ADMIN) {
            token = jwtUtil.generateAdminToken(user.getUsername(), user.getEmail());
        } else {
            token = jwtUtil.generateUserToken(
                user.getUsername(), 
                user.getEmail(), 
                user.getRole().toString().toLowerCase()
            );
        }

        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().toString());
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
