# JWT Authentication with Quarkus SmallRye JWT

This project demonstrates how to implement JWT authentication in a Quarkus application using the `quarkus-smallrye-jwt` extension.

## Features

- JWT token generation and validation
- Role-based access control
- User registration and authentication
- Protected endpoints with different access levels
- OpenAPI documentation with JWT security scheme

## Setup

### 1. Dependencies

The following dependency has been added to `pom.xml`:

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-jwt</artifactId>
</dependency>
```

### 2. Configuration

In `application.properties`:

```properties
# JWT Configuration
mp.jwt.verify.publickey.location=META-INF/resources/publicKey.pem
mp.jwt.verify.issuer=https://example.com/issuer
smallrye.jwt.sign.key.location=META-INF/resources/privateKey.pem
smallrye.jwt.new-token.issuer=https://example.com/issuer
smallrye.jwt.new-token.audience=jwt-audience
```

### 3. RSA Key Pair

RSA keys are generated in `src/main/resources/META-INF/resources/`:
- `publicKey.pem` - For JWT verification
- `privateKey.pem` - For JWT signing

## API Endpoints

### Authentication Endpoints

#### Register User
```
POST /auth/register
Content-Type: application/json

{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password123"
}
```

#### Login User
```
POST /auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

Response:
```json
{
    "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "username": "admin",
    "email": "admin@example.com",
    "role": "ADMIN"
}
```

### Protected Endpoints

All protected endpoints require the `Authorization` header:
```
Authorization: Bearer <JWT_TOKEN>
```

#### User Info (Requires USER or ADMIN role)
```
GET /protected/user
```

#### Admin Info (Requires ADMIN role)
```
GET /protected/admin
```

#### User Profile (Requires Authentication)
```
GET /protected/profile
```

#### Public Endpoint (No authentication required)
```
GET /protected/public
```

## Default Users

The application creates default users on startup:

1. **Admin User**
   - Username: `admin`
   - Password: `admin123`
   - Role: `ADMIN`

2. **Regular User**
   - Username: `user`
   - Password: `user123`
   - Role: `USER`

## Usage Examples

### 1. Login as Admin
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 2. Access Protected Endpoint
```bash
# Save the token from login response
TOKEN="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."

# Access user endpoint
curl -X GET http://localhost:8080/protected/user \
  -H "Authorization: Bearer $TOKEN"

# Access admin endpoint (only works with admin token)
curl -X GET http://localhost:8080/protected/admin \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Register New User
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username":"testuser",
    "email":"test@example.com",
    "password":"password123"
  }'
```

## Testing

Run the tests:
```bash
./mvnw test
```

The test suite includes:
- User registration
- User login
- Protected endpoint access
- Role-based authorization
- Public endpoint access

## OpenAPI Documentation

When the application is running, you can access the OpenAPI documentation at:
- Swagger UI: http://localhost:8080/q/swagger-ui/
- OpenAPI Spec: http://localhost:8080/q/openapi

The API documentation includes JWT authentication configuration and you can test the endpoints directly from the Swagger UI.

## Key Components

1. **JwtUtil** - Utility class for generating JWT tokens
2. **User Entity** - User domain model with roles
3. **AuthService** - Authentication business logic
4. **AuthController** - REST endpoints for authentication
5. **SecuredController** - Protected endpoints demonstrating JWT usage
6. **DataInitializer** - Creates default users on startup

## Security Features

- Password hashing using SHA-256
- Role-based access control (@RolesAllowed)
- JWT token validation
- User registration with duplicate checks
- Secure endpoint protection
