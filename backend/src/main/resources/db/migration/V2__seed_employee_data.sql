INSERT INTO employees (
    employee_code,
    first_name,
    last_name,
    email,
    country,
    department,
    designation,
    joining_date,
    currency,
    created_at,
    updated_at
)
SELECT
    'ACME-' || LPAD(CAST(s AS VARCHAR), 5, '0'),
    CASE s % 10
        WHEN 0 THEN 'Ava' WHEN 1 THEN 'Noah' WHEN 2 THEN 'Mia' WHEN 3 THEN 'Ethan' WHEN 4 THEN 'Olivia' WHEN 5 THEN 'Liam' WHEN 6 THEN 'Sophia' WHEN 7 THEN 'Mason' WHEN 8 THEN 'Charlotte' ELSE 'Lucas'
    END,
    CASE (s * 3) % 10
        WHEN 0 THEN 'Patel' WHEN 1 THEN 'Nguyen' WHEN 2 THEN 'Smith' WHEN 3 THEN 'Muller' WHEN 4 THEN 'Brown' WHEN 5 THEN 'Lee' WHEN 6 THEN 'Singh' WHEN 7 THEN 'Kim' WHEN 8 THEN 'Rossi' ELSE 'Davis'
    END,
    LOWER(CAST(CASE s % 10
        WHEN 0 THEN 'Ava' WHEN 1 THEN 'Noah' WHEN 2 THEN 'Mia' WHEN 3 THEN 'Ethan' WHEN 4 THEN 'Olivia' WHEN 5 THEN 'Liam' WHEN 6 THEN 'Sophia' WHEN 7 THEN 'Mason' WHEN 8 THEN 'Charlotte' ELSE 'Lucas'
    END AS VARCHAR) || '.' || CAST(CASE (s * 3) % 10
        WHEN 0 THEN 'Patel' WHEN 1 THEN 'Nguyen' WHEN 2 THEN 'Smith' WHEN 3 THEN 'Muller' WHEN 4 THEN 'Brown' WHEN 5 THEN 'Lee' WHEN 6 THEN 'Singh' WHEN 7 THEN 'Kim' WHEN 8 THEN 'Rossi' ELSE 'Davis'
    END AS VARCHAR) || s || '@acme.com'),
    CASE s % 5
        WHEN 0 THEN 'USA' WHEN 1 THEN 'India' WHEN 2 THEN 'Germany' WHEN 3 THEN 'United Kingdom' ELSE 'Canada'
    END,
    CASE (s + s % 5) % 5
        WHEN 0 THEN 'Engineering' WHEN 1 THEN 'Finance' WHEN 2 THEN 'Operations' WHEN 3 THEN 'Sales' ELSE 'People Ops'
    END,
    CASE s % 5
        WHEN 0 THEN 'Senior Engineer' WHEN 1 THEN 'Manager' WHEN 2 THEN 'Analyst' WHEN 3 THEN 'Director' ELSE 'Associate'
    END,
    DATEADD('DAY', - ((s % 1825) + 1), CURRENT_DATE),
    CASE s % 5
        WHEN 0 THEN 'USD' WHEN 1 THEN 'INR' WHEN 2 THEN 'EUR' WHEN 3 THEN 'GBP' ELSE 'CAD'
    END,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM SYSTEM_RANGE(1, 10000) AS x(s);

INSERT INTO salary_records (employee_id, annual_salary, currency, effective_from, created_at, created_by)
SELECT e.id, 50000 + ((e.id * 791) % 140000), e.currency, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system-seed'
FROM employees e;

INSERT INTO salary_audit (employee_id, previous_salary, new_salary, currency, reason, changed_by, changed_at)
SELECT e.id, NULL, sr.annual_salary, e.currency, 'Initial seed record', 'system-seed', CURRENT_TIMESTAMP
FROM employees e
JOIN salary_records sr ON sr.employee_id = e.id;
