# ACME Salary Management — Architecture

```text
                    ┌──────────────────────┐
                    │      Angular UI      │
                    │ Angular Material     │
                    └──────────┬───────────┘
                               │ REST/JSON
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │                      │
                    │ Employee Module      │
                    │ Salary Module        │
                    │ Analytics Module     │
                    │ Audit Module         │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     PostgreSQL       │
                    │                      │
                    │ Employees            │
                    │ Salary Records       │
                    │ Audit Records        │
                    └──────────────────────┘
```

## Why a modular monolith?
The application has a small number of closely related business capabilities, a single primary user persona, and a dataset of approximately 10,000 employees. A modular monolith keeps the system easier to reason about while still providing clear boundaries between employee management, salary updates, analytics, and audit trails. It also reduces delivery friction for a focused assessment without introducing the operational complexity of microservices.

## Module boundaries
- employee: employee profiles and search/filtering.
- salary: current salary updates and historical records.
- analytics: summary and aggregation reports.
- audit: compensation change tracking and history.
- common: shared configuration, validation, and exception handling.

## Data model summary
- employees: core employee identity and current job metadata.
- salary_records: historical salary entries for each employee.
- salary_audit: immutable compensation change log with reason, actor, and timestamp.

## Performance and scale
- Server-side pagination and filtering keep UI responsiveness stable.
- Search and analytics leverage database indexes and aggregate queries.
- This is intentionally sized for 10,000 employees rather than a distributed high-scale enterprise environment.
