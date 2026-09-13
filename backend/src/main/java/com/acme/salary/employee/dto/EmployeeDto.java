package com.acme.salary.employee.dto;

import java.time.LocalDate;

public record EmployeeDto(
        Long id,
        String employeeCode,
        String firstName,
        String lastName,
        String email,
        String country,
        String department,
        String designation,
        LocalDate joiningDate,
        String currency
) {
}
