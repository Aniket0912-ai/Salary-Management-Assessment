# ACME Salary Management — Tradeoffs

## Why not microservices?
This is a single-product HR compensation domain with a small set of closely related capabilities. A modular monolith keeps the system easier to build, test, and reason about for an assessment while still preserving clear module boundaries.

## FX handling
The MVP stores salary in the employee's native currency and uses deterministic seeded FX rates for analytics. This avoids external API coupling while keeping reporting consistent and reproducible.

## Audit model
The application keeps a full salary audit trail instead of overwriting historical compensation entries. That adds slight write overhead but greatly improves trust and reviewability.

## Scope choice
The project deliberately avoids payroll execution, tax calculation, attendance, leave management, and scheduling because these are not necessary to answer the core HR question: how the organization pays people.
