package com.acme.salary.employee.dto;

import java.util.List;

public record DashboardSummaryDto(
        long totalEmployees,
        double annualPayroll,
        double averageSalary,
        double salaryVariance,
        List<DepartmentSummaryDto> departmentBreakdown,
        List<SalaryBandDto> salaryBands
) {
}

