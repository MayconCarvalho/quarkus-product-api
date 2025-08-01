package com.api.auth.controller;

import com.api.auth.application.dto.LoginRequest;
import com.api.auth.application.dto.RegisterRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AuthControllerTest {

    @Test
    public void testRegisterUser() {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "password123");
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/auth/register")
        .then()
            .statusCode(201)
            .body("token", notNullValue())
            .body("username", is("testuser"))
            .body("email", is("test@example.com"))
            .body("type", is("Bearer"));
    }

    @Test
    public void testLoginUser() {
        LoginRequest request = new LoginRequest("admin", "admin123");
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue())
            .body("username", is("admin"))
            .body("role", is("ADMIN"));
    }

    @Test
    public void testProtectedEndpointWithoutToken() {
        given()
        .when()
            .get("/protected/user")
        .then()
            .statusCode(401);
    }

    @Test
    public void testProtectedEndpointWithToken() {
        // First login to get token
        LoginRequest loginRequest = new LoginRequest("admin", "admin123");
        
        String token = given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Use token to access protected endpoint
        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/protected/user")
        .then()
            .statusCode(200)
            .body("username", is("admin"))
            .body("message", notNullValue());
    }

    @Test
    public void testAdminEndpointWithUserToken() {
        // Login as regular user
        LoginRequest loginRequest = new LoginRequest("user", "user123");
        
        String token = given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Try to access admin endpoint (should fail)
        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/protected/admin")
        .then()
            .statusCode(403); // Forbidden
    }

    @Test
    public void testPublicEndpoint() {
        given()
        .when()
            .get("/protected/public")
        .then()
            .statusCode(200)
            .body("message", notNullValue());
    }
}
