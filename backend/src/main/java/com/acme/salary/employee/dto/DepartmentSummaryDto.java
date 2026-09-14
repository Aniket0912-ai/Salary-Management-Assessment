package com.acme.salary.employee.dto;

public record DepartmentSummaryDto(
        String name,
        long headcount,
        double payroll,
        double share
) {
}
