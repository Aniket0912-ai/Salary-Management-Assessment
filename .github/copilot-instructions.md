# ACME Salary Management — GitHub Copilot Instructions

## Project Context
We are building an employee salary management platform for ACME, an organization with approximately 10,000 employees across multiple countries. The primary user is an HR Manager. The application replaces spreadsheet-based salary management with a web application that provides employee search, filtering, sorting, pagination, salary management, salary history, auditability, and organization-wide compensation analytics.

This is an assessment project. Prioritize correctness, maintainability, product thinking, testability, and simplicity over unnecessary complexity.

## Technology Stack
### Backend
- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Bean Validation
- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers where appropriate
- OpenAPI/Swagger

### Frontend
- Angular
- Angular Material
- TypeScript
- RxJS

Build a modular monolith. Do not introduce microservices unless explicitly requested.

## Architecture
Organize the backend by business capability:
- employee
- salary
- analytics
- audit
- common

Each module should have appropriate controller, service, repository, entity, and DTO classes.

Controllers must not contain business logic. Services contain business rules. Repositories contain persistence concerns. DTOs must be used at API boundaries rather than exposing JPA entities directly.

## Database
Use PostgreSQL in the main environment. Use Flyway migrations for schema changes.

Required tables include:
- employees
- salary_records
- salary_audit

Preserve salary history. Do not overwrite historical salary records.

## API Principles
- Use RESTful APIs
- Support pagination, sorting, filtering, and search
- Never return all 10,000 employee records by default
- Use server-side pagination
- Use appropriate HTTP status codes
- Use consistent error responses
- Validate request payloads using Jakarta Bean Validation

## Salary Rules
- Salary must be positive
- Currency must be valid
- An employee must exist before salary can be updated
- Updating salary must validate the request, retrieve the employee, retrieve the current salary, persist the new salary, create salary history, and create an audit record in one transaction
- Do not silently discard previous salary information

## Currency
Employees can have salaries in different currencies. Always store salary together with its currency. In the MVP, deterministic seeded FX rates are acceptable for cross-country reporting.

## Analytics
The application should support:
- total employee count
- total payroll
- average salary
- median salary
- salary range
- average salary by country
- average salary by department
- payroll by department
- salary distribution
- highest-paid employees

Prefer database aggregation queries over loading all employee data into memory.

## Frontend
Use Angular Material. Recommended screens:
- /dashboard
- /employees
- /employees/:id
- /analytics

Use reusable components and API services. Use loading, empty, and error states. Tables must support pagination and sorting. Avoid downloading all 10,000 records to the browser.

## Testing
Write tests for business-critical behavior. Prefer behavior-focused names such as shouldCreateSalaryAuditWhenSalaryIsUpdated().

## Error Handling
Implement centralized exception handling and meaningful API errors.

## Security
This assessment has one primary persona: HR Manager. Do not build a complete enterprise authentication system unless explicitly requested. Use environment variables, do not hardcode secrets, and design the API so authentication can be added later.

## Seed Data
Provide a deterministic seed mechanism for 10,000 employees with realistic variation across countries, departments, designations, currencies, salaries, and joining dates.

## Code Quality
Prefer small focused methods, meaningful names, immutable DTOs, constructor injection, and clear separation of concerns.

## Documentation
Maintain the project documentation under docs and a clear README.

## AI Usage
Use AI to accelerate implementation, but never blindly accept generated code. Review business logic, edge cases, database queries, and security implications before finalizing code.
