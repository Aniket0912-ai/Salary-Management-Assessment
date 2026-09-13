# ACME Salary Management — Requirements

## 1. Goal
Build a web-based salary management application that enables ACME's HR managers to securely manage compensation data for approximately 10,000 employees across multiple countries and quickly understand how the organization pays its employees.

## 2. Primary User
HR Manager — responsible for maintaining employee compensation information and analyzing salary patterns across countries, departments, and roles.

## 3. MVP Scope
### Employee & Salary Management
- View, search, filter, sort, and paginate employee records.
- View employee profile and current compensation.
- Update an employee's salary and currency.
- Maintain salary history for every salary change.
- Record the reason, timestamp, and actor for salary changes.

### Compensation Dashboard
Provide high-level organizational metrics:
- Total employees.
- Total payroll.
- Average and median salary.
- Salary range.
- Employee distribution by country and department.

### Compensation Analytics
Allow HR managers to answer questions such as:
- What is the average salary by country?
- What is the average salary by department?
- Which departments contribute most to total payroll?
- What is the salary distribution?
- Who are the highest-paid employees?
- What percentage of employees fall into a given salary range?

### Data & Reliability
- Seed the application with 10,000 realistic employee records.
- Store salary in the employee's local currency.
- Use deterministic FX rates for cross-currency reporting in the MVP.
- Provide API validation and meaningful error responses.
- Maintain salary-change audit history.

## 4. Non-Goals
The MVP deliberately excludes payroll execution, tax calculation, benefits, bonuses, payslip generation, attendance, leave management, recruitment, payment integrations, SSO, multi-tenancy, and real-time notifications.

## 5. Key Product Decisions
- Use server-side pagination and filtering rather than loading all 10,000 employees into the browser.
- Store salary together with its currency to avoid ambiguous compensation values.
- Preserve salary history rather than overwriting compensation data.
- Use indexed database queries for frequently filtered fields such as country, department, and employee name.
- Keep the application as a modular monolith because the current domain and scale do not justify microservices.

## 6. Success Criteria
The HR manager should be able to:
- Find an employee quickly.
- Update compensation safely.
- See the employee's salary history.
- Understand organization-wide compensation through dashboards and analytics.
- Reliably work with 10,000 seeded employees without noticeable UI degradation.

The system should have automated tests covering core business logic, validation, salary updates, audit history, analytics, and API behavior.
