# ACME Salary Management — Performance

## Strategy
- Use server-side pagination for the employee table.
- Keep filtering and sorting in the database.
- Add indexes for common query fields.
- Aggregate analytics in the database rather than in application memory.

## Expected scale
The application is designed for roughly 10,000 employees and should remain responsive without naive full-table client downloads.

## Key indexes
- employees.country
- employees.department
- employees.last_name
- employees.employee_code
- salary_records.employee_id
- salary_audit.employee_id
