# AppointmentBookingSystem

A Spring Boot backend for an appointment management system with JWT authentication, role-based authorization, validation, global exception handling, pagination, and MySQL persistence.

## Features

- User registration and login
- JWT authentication
- Role-based access control (`USER`, `ADMIN`)
- Create, update, delete, and view users
- Create, update, delete, and view appointments
- Search appointments by user email or name
- Current logged-in user endpoint
- Ownership checks for appointment modification
- Validation with `@Valid`
- Centralized exception handling
- Pagination support
- Swagger/OpenAPI documentation

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT
- Maven
- JUnit 5
- Mockito
- Swagger/OpenAPI

## Project Structure

- `controller` – REST endpoints
- `service` – business logic
- `repository` – database access
- `entity` – JPA entities
- `dto` – request/response models
- `converter` – entity/DTO mapping
- `exception` – custom exceptions and handler
- `config` – security and JWT configuration

## Authentication

Login returns a JWT token.  
Use it in requests like this:

`Authorization: Bearer <your_token>`

## API Endpoints

### Auth
- `POST /api/auth/login`

### Users
- `POST /api/users`
- `GET /api/users`
- `GET /api/users/{id}`
- `GET /api/users/me`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

### Appointments
- `POST /api/appointments`
- `GET /api/appointments`
- `GET /api/appointments/{id}`
- `GET /api/appointments/my`
- `GET /api/appointments/search?email=&name=`
- `PUT /api/appointments/{id}`
- `DELETE /api/appointments/{id}`

## How to Run

1. Clone the repository
2. Configure MySQL in `application-local.properties`
3. Run the application from IntelliJ or using Maven
4. Open Swagger UI in the browser

## Future Improvements

- Refresh token support
- Email notifications
- Docker support
- CI/CD pipeline
- Appointment time conflict validation
- Better admin dashboard
- User-to-user booking model
