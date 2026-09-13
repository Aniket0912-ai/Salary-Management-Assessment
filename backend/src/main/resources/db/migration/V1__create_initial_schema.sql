CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    designation VARCHAR(150) NOT NULL,
    joining_date DATE NOT NULL,
    currency VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE salary_records (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    annual_salary DECIMAL(19,4) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    effective_from TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL,
    CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE salary_audit (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    previous_salary DECIMAL(19,4),
    new_salary DECIMAL(19,4) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    changed_by VARCHAR(100) NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE INDEX idx_employees_country ON employees(country);
CREATE INDEX idx_employees_department ON employees(department);
CREATE INDEX idx_employees_last_name ON employees(last_name);
CREATE INDEX idx_employees_employee_code ON employees(employee_code);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_salary_employee_id ON salary_records(employee_id);
CREATE INDEX idx_audit_employee_id ON salary_audit(employee_id);
