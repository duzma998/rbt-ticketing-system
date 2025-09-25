# RBT Ticketing System

## Overview
RBT Ticketing System is a modern event ticketing platform built with Spring Boot that allows users to browse events, create orders, and manage tickets through REST APIs. The system features JWT-based authentication, event management, order processing, and ticket reservation capabilities.

## Features
- User registration and authentication with JWT tokens
- Event management (create, read, update, delete)
- Order creation for event tickets
- Ticket reservation system with seat assignment
- Kafka integration for event-driven order processing
- Comprehensive validation and error handling
- Role-based access control

## Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Apache Kafka (for event-driven features)
- Maven 3.6+ (for building)

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/duzma998/rbt-ticketing-system
cd rbt-ticketing-system
```

### 2. Configuration
Update `application.properties` with your configuration:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ticketing_db
spring.datasource.username=ticketing_user
spring.datasource.password=ticketing_pass
spring.kafka.bootstrap-servers=localhost:9094
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=false
spring.flyway.locations=classpath:database/migration
security.jwt.secret-key=your-super-secret-jwt-key-here
security.jwt.expiration=480
```

### 3. Build and Run
```bash
docker-compose up -d
```

## API Usage

### Authentication Endpoints

#### User Roles
- `ADMIN`
- `USER`

### Test admin
- Username: `testadmin`
- Password: **provided in email**

#### User Registration
```http
POST /api/v1/users/signup
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "firstname": "John",
  "lastname": "Doe"
}
```

#### User Login
```http
POST /api/v1/users/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

Response:
```json
{
  "username": "john_doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Event Management

#### Get Public Events (Paginated)
```http
GET /api/v1/events?page=0&size=20
```

#### Create New Event (Requires ADMIN Role)
```http
POST /api/v1/events
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "Concert Night",
  "description": "Amazing concert with top artists",
  "eventType": "CONCERT",
  "venueName": "Grand Theater",
  "venueAddress": "123 Music Street, City",
  "eventDate": "2024-12-25T20:00:00Z",
  "totalTickets": 1000,
  "maxTicketsPerPurchase": 10,
  "ticketPrice": 50.0,
  "status": "ACTIVE"
}
```

#### Get Event by ID
```http
GET /api/v1/events/{id}
```

#### Update Event (Requires ADMIN Role)
```http
PATCH /api/v1/events/{id}
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "Updated Concert Name",
  "ticketPrice": 55.0
}
```

#### Cancel Event (Requires ADMIN Role)
```http
POST /api/v1/events/{id}/cancel
Authorization: Bearer <your-jwt-token>
```

### Order Management

#### Create Order (Requires Authentication)
```http
POST /api/v1/orders
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "username": "john_doe",
  "eventId": 1,
  "ticketCount": 2,
  "seats": ["1", "2"]
}
```

#### Create Order via Kafka (Event-Driven)
Send JSON message to Kafka topic `RESERVE_TICKETS`:
```json
{
  "username": "john_doe",
  "eventId": 1,
  "ticketCount": 2,
  "seats": ["1", "2"]
}
```

### Ticket Management

#### Use Ticket
```http
POST /api/v1/tickets/{ticketCode}/use
Authorization: Bearer <your-jwt-token>
```

#### Validate Ticket
```http
POST /api/v1/tickets/{ticketCode}/validate
Authorization: Bearer <your-jwt-token>
```

#### Cancel Ticket
```http
POST /api/v1/tickets/{ticketCode}/cancel
Authorization: Bearer <your-jwt-token>
```

## Authentication Flow

1. **Register** a new user via `/api/v1/users/signup`
2. **Login** to get JWT token via `/api/v1/users/login`
3. **Use Token** in all authenticated requests:
   ```
   Authorization: Bearer <jwt-token>
   ```

## Swagger
Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`