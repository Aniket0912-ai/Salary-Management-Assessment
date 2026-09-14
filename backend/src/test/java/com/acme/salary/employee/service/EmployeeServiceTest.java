package com.acme.salary.employee.service;

import com.acme.salary.common.exception.ResourceNotFoundException;
import com.acme.salary.employee.dto.DashboardSummaryDto;
import com.acme.salary.employee.dto.EmployeeDto;
import com.acme.salary.employee.entity.Employee;
import com.acme.salary.employee.repository.EmployeeRepository;
import com.acme.salary.employee.repository.SalaryRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryRecordRepository salaryRecordRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldSearchEmployeesAndMapToDto() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeCode("ACME-00001");
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setEmail("john.smith@acme.com");
        employee.setCountry("USA");
        employee.setDepartment("Engineering");
        employee.setDesignation("Senior Engineer");
        employee.setJoiningDate(LocalDate.of(2020, 1, 10));
        employee.setCurrency("USD");

        when(employeeRepository.findByFilters(eq("john"), eq("USA"), eq("Engineering"), any())).thenReturn(new PageImpl<>(List.of(employee)));

        Page<EmployeeDto> result = employeeService.searchEmployees("john", "USA", "Engineering", PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().getFirst().firstName());
        assertEquals("USA", result.getContent().getFirst().country());
    }

    @Test
    void shouldComputeDashboardSummary() {
        when(employeeRepository.count()).thenReturn(10000L);
        when(salaryRecordRepository.getTotalAnnualPayroll()).thenReturn(42000000.0);
        when(salaryRecordRepository.getAverageAnnualSalary()).thenReturn(126000.0);
        when(salaryRecordRepository.getVariancePercent()).thenReturn(12.4);
        when(salaryRecordRepository.getDepartmentPayrollSummary()).thenReturn(List.of(
                new Object[]{"Engineering", 2480L, 14800000.0, 34.7},
                new Object[]{"Sales", 1690L, 9300000.0, 21.9}
        ));
        when(salaryRecordRepository.getSalaryBandSummary()).thenReturn(List.of(
                new Object[]{"0-60K", 1420L},
                new Object[]{"60-100K", 2680L}
        ));

        DashboardSummaryDto summary = employeeService.getDashboardSummary();

        assertEquals(10000L, summary.totalEmployees());
        assertEquals(42000000.0, summary.annualPayroll());
        assertEquals(126000.0, summary.averageSalary());
        assertEquals(12.4, summary.salaryVariance());
        assertEquals(2, summary.departmentBreakdown().size());
        assertEquals(2, summary.salaryBands().size());
    }

    @Test
    void shouldThrowWhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployee(99L));
        assertTrue(ex.getMessage().contains("Employee not found"));
    }
}
