# ACME Salary Management — API Design

## Core endpoints

### Employees
- GET /api/employees
  - query params: search, country, department, page, size, sort
- GET /api/employees/{id}
- PUT /api/employees/{id}/salary
- GET /api/employees/{id}/salary-history

### Analytics
- GET /api/analytics/summary
- GET /api/analytics/by-country
- GET /api/analytics/by-department
- GET /api/analytics/salary-distribution
- GET /api/analytics/top-earners

## Design principles
- DTO-first API contract
- Server-side filtering and paging
- Validation with Bean Validation
- Clear error envelopes
- Transactional salary updates

## Example response shape

```json
{
  "id": 123,
  "employeeCode": "ACME-00123",
  "firstName": "John",
  "lastName": "Smith",
  "country": "USA",
  "department": "Engineering",
  "currency": "USD"
}
```
