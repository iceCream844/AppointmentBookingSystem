# 📅 Appointment Booking System Backend

A secure RESTful backend application built with **Spring Boot 3** that enables users to register, authenticate using JWT, and manage appointments with role-based authorization.

This project was developed as part of my backend portfolio to demonstrate Java backend development skills including:

- RESTful API Design
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Validation
- Exception Handling
- Unit Testing

---

# Features

## Authentication

- User Registration
- User Login using JWT
- Password Encryption (BCrypt)
- Stateless Authentication

## User Management

- Create User
- Get User by ID
- Update User
- Delete User
- Get Current Logged-in User
- Pagination

## Appointment Management

- Create Appointment
- Update Appointment
- Delete Appointment
- Search Appointment by User Email
- Search Appointment by User Name
- Get Current User Appointments

## Security

- JWT Authentication
- Spring Security
- Role-Based Authorization
- Ownership Validation
- Protected REST APIs

## Validation & Error Handling

- Bean Validation (`@Valid`)
- Global Exception Handler
- Custom Exceptions

---

# Architecture

```
            React / Postman
                    │
                    ▼
          REST Controllers
                    │
                    ▼
             Service Layer
                    │
                    ▼
         Spring Data JPA
                    │
                    ▼
                 MySQL
```

---

# Technology Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot 3.2.5 | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | ORM |
| Hibernate | Persistence |
| MySQL | Database |
| JWT | Authentication |
| Maven | Dependency Management |
| JUnit 5 | Unit Testing |
| Mockito | Mocking Framework |
| Swagger/OpenAPI | API Documentation |
| Postman | API Testing |

---

# Project Structure

```
src
├── config
├── controller
├── converter
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── repository
├── service
│   └── impl
└── AppointmentBookingApplication
```

---

# Authentication

After a successful login, the backend returns a JWT token.

Example response:

```json
{
  "token": "<JWT_TOKEN>"
}
```

Include the token in the Authorization header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Authorization

This project uses **Role-Based Access Control (RBAC)**.

| Role | Permissions |
|------|-------------|
| USER | Manage own appointments and view own profile |
| ADMIN | Manage all users and appointments |

Business Rules:

- Users can only update or delete their own appointments.
- Administrators can manage all users and appointments.

---

# REST API

## Authentication

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/auth/login` | Login |

## Users

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/users` | Register User |
| GET | `/api/users` | Get All Users |
| GET | `/api/users/{id}` | Get User by ID |
| GET | `/api/users/me` | Current User |
| PUT | `/api/users/{id}` | Update User |
| DELETE | `/api/users/{id}` | Delete User |

## Appointments

| Method | Endpoint | Description |
|----------|----------|-------------|
| POST | `/api/appointments` | Create Appointment |
| GET | `/api/appointments` | Get All Appointments |
| GET | `/api/appointments/{id}` | Get Appointment by ID |
| GET | `/api/appointments/my` | Current User Appointments |
| GET | `/api/appointments/search` | Search Appointment |
| PUT | `/api/appointments/{id}` | Update Appointment |
| DELETE | `/api/appointments/{id}` | Delete Appointment |

---

# Running the Project

## Clone Repository

```bash
git clone https://github.com/iceCream844/AppointmentBookingSystem.git
```

```bash
cd AppointmentBookingSystem
```

## Configure Database

Create `application-local.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/appointment_booking
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

jwt.secret=YOUR_SECRET_KEY
```

## Run

Using IntelliJ:

Run `AppointmentBookingApplication`

or

```bash
mvn spring-boot:run
```

---

# API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Testing

Unit tests are implemented using:

- JUnit 5
- Mockito

Covered services:

- UserServiceImpl
- AppointmentServiceImpl

---

# Postman Collection

The repository includes a Postman Collection for testing all REST APIs.

Import the collection into Postman and configure the environment variables before testing authenticated endpoints.

---

# Future Improvements

- Refresh Token Authentication
- Email Notifications
- Appointment Reminder Scheduler
- Docker Support
- CI/CD Pipeline
- Appointment Time Conflict Validation
- User-to-User Booking Model
- Integration Tests

---

# About

This project was developed as part of my software engineering portfolio to strengthen my backend development skills using Spring Boot and modern Java technologies.

It demonstrates secure REST API development, layered architecture, authentication, authorization, validation, exception handling, and unit testing.
