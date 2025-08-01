# Product Registration API

A RESTful API for product management built with Quarkus, Java 21, and PostgreSQL following Clean Architecture principles.

## Project Overview

This API provides endpoints for creating, reading, updating, and deleting product information. It uses a PostgreSQL database for persistent storage and follows Clean Architecture for better separation of concerns and maintainability.

## Technology Stack

- Java 21
- Quarkus 3.6.4
- PostgreSQL 17
- Docker & Docker Compose
- Kubernetes (optional deployment)
- Spring Data JPA
- Swagger/OpenAPI 3

## Clean Architecture Layers

The project is organized according to Clean Architecture principles:

### Domain Layer
- Located in `com.api.product.domain`
- Contains the core business entities like `Product`
- No dependencies on other layers or frameworks

### Application Layer
- Located in `com.api.product.application`
- Contains business logic in services
- Defines DTOs for data transfer
- Mappers to convert between entities and DTOs

### Infrastructure Layer
- Located in `com.api.product.infrastructure`
- Contains database entities in `entity` package
- JPA repository implementations
- Mappers to convert between domain and database entities

### Presentation Layer
- Located in `com.api.product.presentation`
- Contains REST controllers that expose the API endpoints
- Uses DTOs for request/response data

## Directory Structure

## Running Locally

### Prerequisites
- Docker and Docker Compose installed
- Kubernetes cluster (Minikube, Kind, or Docker Desktop Kubernetes) for Kubernetes deployment
- kubectl CLI installed for Kubernetes deployment

### Using Docker Compose

1. Build and start the application:
   ```bash
   ./mvnw package
   docker-compose up -d
   ```

2. Access the application:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/q/swagger-ui

3. Stop the application:
   ```bash
   docker-compose down
   ```

### Using Kubernetes

1. Build the Docker image:
   ```bash
   ./mvnw package
   docker build -t product-api:latest .
   ```

2. Deploy to Kubernetes:
   ```bash
   kubectl apply -f kubernetes/
   ```

3. Check the deployment status:
   ```bash
   kubectl get pods
   kubectl get services
   ```

4. Access the application:
   ```bash
   # Get the service URL (if using Minikube)
   minikube service product-api --url
   
   # For other Kubernetes distributions, check the service's external IP
   kubectl get service product-api
   ```

5. Delete the deployment:
   ```bash
   kubectl delete -f kubernetes/
   ```

## Environment Configuration

The application uses the following environment variables that can be configured in Docker Compose or Kubernetes:

| Variable | Description | Default |
|----------|-------------|---------|
| QUARKUS_DATASOURCE_URL | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/product_db |
| QUARKUS_DATASOURCE_USERNAME | Database username | postgres |
| QUARKUS_DATASOURCE_PASSWORD | Database password | postgres |
| QUARKUS_HTTP_PORT | Application port | 8080 |

For more configuration options, refer to the `application.properties` file.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /api/products | Get all products |
| GET    | /api/products/{id} | Get product by ID |
| POST   | /api/products | Create a new product |
| PUT    | /api/products/{id} | Update an existing product |
| DELETE | /api/products/{id} | Delete a product |
| GET    | /api/products/search?name={name} | Search products by name |
| GET    | /api/products/price-range?min={min}&max={max} | Get products within price range |
| GET    | /api/products/in-stock | Get products in stock |

## Development Setup

### Prerequisites
- Java 21 or later
- Maven 3.8 or later
- PostgreSQL 17 installed locally or accessible

### Setting Up Local Development Environment

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/product-api.git
   cd product-api
   ```

2. Configure the database connection in `src/main/resources/application.properties`

3. Run the application in development mode:
   ```bash
   ./mvnw quarkus:dev
   ```
   This will start the application with hot reload enabled on http://localhost:8080

## Testing

### Running Tests

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify
```

### Test Coverage

```bash
./mvnw verify -Pcoverage
```
Coverage reports can be found in `target/site/jacoco/index.html`

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Verify PostgreSQL is running
   - Check connection details in application.properties
   - Ensure network connectivity between the application and database

2. **Kubernetes Deployment Issues**
   - Verify kubectl context is set to the correct cluster
   - Check pod logs: `kubectl logs -f <pod-name>`
   - Verify secrets exist: `kubectl get secrets`

3. **Build Failures**
   - Clear Maven cache: `./mvnw clean`
   - Verify Java version matches requirements

## Performance Considerations

- The API includes database indexing on frequently queried fields
- Connection pooling is configured for optimal database performance
- Products are loaded with pagination to handle large datasets efficiently
- Consider using a caching solution for production deployments

## Security

- API inputs are validated to prevent injection attacks
- Database credentials are stored as Kubernetes secrets
- CORS is enabled and can be configured in application.properties
- Consider implementing authentication for production use

## Project Structure

```
├── src
│   ├── main
│   │   ├── java/com/api/product
│   │   │   ├── domain            # Core domain entities
│   │   │   ├── application       # Application services and DTOs
│   │   │   ├── infrastructure    # DB entities and repositories
│   │   │   └── presentation      # REST controllers
│   │   └── resources
│   │       └── application.properties
│   └── test
│       ├── java
│       └── resources
├── kubernetes                     # K8s deployment files
├── .mvn
├── docker-compose.yml
└── pom.xml
```

