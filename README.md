# ACME Salary Management

A production-minded salary management and compensation analytics platform designed for HR teams managing 10,000 employees across multiple countries.

## Key capabilities

- Employee salary management
- Salary history and audit trail
- Multi-country / multi-currency support
- Compensation analytics
- Server-side search and pagination
- 10,000 deterministic seed records
- Automated tests
- API documentation
- Containerized deployment

## Architecture

The application is implemented as a modular monolith using Java 21 and Spring Boot 3.x on the backend, with Angular and Angular Material on the frontend. Domain capabilities are split into modules such as employee, salary, analytics, and audit rather than building microservices.

## Technology stack

- Backend: Java 21, Spring Boot 3.x, Spring Web, Spring Data JPA, Hibernate, PostgreSQL, Flyway
- Frontend: Angular, Angular Material, TypeScript, RxJS
- Testing: JUnit 5, Mockito, Spring Boot Test, Testcontainers
- API docs: Springdoc OpenAPI
- Deployment: Docker Compose, Render/Railway/Fly.io ready

## Local setup

1. Install Java 21 and Node.js 20+
2. Start the PostgreSQL database via Docker Compose
3. Run the backend:
   - cd backend
   - ./mvnw spring-boot:run
4. Run the frontend:
   - cd frontend
   - npm install
   - npm start
5. Open the application in the browser and visit the Angular app

## Database

The backend uses PostgreSQL in the deployed environment and H2/Testcontainers for tests. Flyway manages schema evolution in the database.

## Seed data

Seed scripts are included under scripts/seed to generate deterministic employee records for realistic testing and demo scenarios.

## API documentation

Swagger/OpenAPI is available through the Spring Boot application once the backend is running, typically at /swagger-ui.html or /swagger-ui/index.html depending on the version.

## Testing

Backend tests can be run with:

- cd backend
- ./mvnw test

Frontend tests can be run with:

- cd frontend
- npm test -- --watch=false

## Deployment

The repository includes Docker Compose configuration to support a local multi-service stack and can be adapted for Render, Railway, or Fly.io.

## Design decisions

- Server-side pagination rather than loading all 10,000 employees into the browser
- Salary stored with currency to avoid ambiguous compensation data
- Audit history retained for every salary change
- Modular monolith selected over microservices for this domain and scale
- Deterministic FX usage is documented as a deliberate MVP trade-off instead of a live external API dependency

## AI usage

GitHub Copilot was used throughout the project to accelerate scaffolding, documentation, test-case design, and review. All generated code was verified against the business requirements and automated tests before being retained.

## Demo

The product demonstrates a clean HR workflow for salary inspection, filtering, updates, and compensation analytics without overreaching into payroll execution or employee management outside the salary domain.
