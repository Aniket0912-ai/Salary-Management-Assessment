# ACME Salary Management Assessment Prompt

You are working on an assessment project for ACME, an organization with approximately 10,000 employees across multiple countries. The goal is to build a salary management system for HR managers that replaces spreadsheets with a modern web application.

## Product goal
Build software that helps HR managers:
- search and explore employee compensation data
- review current salaries and history
- filter by country, department, designation, and salary band
- understand org-wide pay patterns and trends
- safely update salaries with auditability

## Non-goals
Do not build payroll execution, tax systems, attendance, recruitment, benefits, or multi-tenant enterprise auth. Keep the scope to salary management and compensation insights.

## Architecture preference
Use a modular monolith with a clear business-capability split. Keep the design simple and maintainable. Avoid unnecessary microservices.

## Stack
- Backend: Java 21 + Spring Boot 3
- Frontend: Angular + Angular Material
- Database: PostgreSQL or SQLite-compatible development database with clear migration strategy
- Testing: JUnit 5 + Mockito
- Documentation: README + docs folder with requirements, architecture, trade-offs, API design, and performance notes

## Implementation expectations
- Build a complete end-to-end product with backend, UI, and seed data
- Include server-side pagination and filtering
- Preserve salary history via audit records
- Support analytics over salary data
- Keep the UI attractive, polished, and interactive
- Include a realistic 10,000-employee seed script
- Ensure tests cover key business functionality and validation

## AI usage expectations
Use AI to accelerate scaffolding, code generation, tests, and docs, but review every output critically. Prefer correctness and maintainability over raw speed.

## Commit philosophy
Make incremental commits that show the project evolution:
1. requirements and architecture
2. backend foundation
3. employee and salary domain logic
4. analytics and audits
5. frontend dashboard and UX polish
6. verification and cleanup

## Deliverable quality bar
Ship something that feels like a real HR product: clear dashboards, strong UX, maintainable code, meaningful tests, and thoughtful product decisions.
