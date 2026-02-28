# Cook Systems Project Management Dashboard

Full-stack project management dashboard for managing companies, teams, users, projects, and announcements.

This repository contains:
- `server` - Spring Boot REST API
- `client/client-app` - Angular frontend

## What This Project Is

The dashboard is built for internal company/project coordination. It supports:
- User login and role-aware access patterns
- Company selection and context switching
- Team and project views (including project creation and updates)
- Company announcements
- User management

The backend seeds starter data on application startup, so the app is usable immediately in development.

## Skills and Technologies Used

### Frontend
- Angular 19
- TypeScript
- RxJS (`BehaviorSubject`, observable state)
- Angular Router and route guards
- HttpClient for API integration
- Component-based UI architecture

### Backend
- Java 21
- Spring Boot 3 (Web + Data JPA)
- PostgreSQL
- Spring Data repositories
- MapStruct for DTO/entity mapping
- Lombok for boilerplate reduction
- BCrypt password hashing
- RESTful controller/service/repository layering

### Engineering Skills Demonstrated
- Full-stack API design and integration
- Authentication and authorization guard patterns
- Relational data modeling and ORM usage
- DTO-based API contracts
- Seed data bootstrapping for local development
- Separation of concerns across frontend/backend layers

## Project Structure

```text
cook-systems-project-management-dashboard/
  server/                 # Spring Boot API
  client/
    client-app/           # Angular application
```

## Prerequisites

Install the following before setup:
- Java 21
- Maven 3.9+ (or use the included Maven wrapper in `server`)
- Node.js 20+ and npm
- PostgreSQL 14+ (or compatible version)

## Setup

### 1) Configure PostgreSQL

By default, the backend expects:
- Host: `localhost`
- Port: `5432`
- Database: `postgres`
- Username: `postgres`
- Password: set in `server/src/main/resources/application.properties`

If your local database credentials differ, update:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

in `server/src/main/resources/application.properties`.

### 2) Run the Backend (Spring Boot)

From the `server` directory:

```bash
./mvnw spring-boot:run
```

On Windows Command Prompt:

```bat
mvnw.cmd spring-boot:run
```

The API runs at:
- `http://localhost:8080`

Notes:
- `spring.jpa.hibernate.ddl-auto=create-drop` is enabled, so schema/data reset on restart.
- Seeder runs at startup and inserts sample companies, users, teams, announcements, and projects.

### 3) Run the Frontend (Angular)

From `client/client-app`:

```bash
npm install
npm start
```

The frontend runs at:
- `http://localhost:4200`

## Seeded Login for Local Development

The backend seeder creates sample users:
- `admin` / `password`
- `worker` / `password`

## Useful Commands

### Frontend
- Run dev server: `npm start`
- Build: `npm run build`
- Unit tests: `npm test`

### Backend
- Run app: `./mvnw spring-boot:run`
- Run tests: `./mvnw test`

## Notes

- API base URL is hardcoded to `http://localhost:8080` in frontend services.
- Ensure backend is running before logging in from the frontend.
