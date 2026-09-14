package com.acme.salary.employee.service;

import com.acme.salary.common.exception.ResourceNotFoundException;
import com.acme.salary.employee.dto.CreateEmployeeRequest;
import com.acme.salary.employee.dto.DashboardSummaryDto;
import com.acme.salary.employee.dto.DepartmentSummaryDto;
import com.acme.salary.employee.dto.EmployeeDto;
import com.acme.salary.employee.dto.SalaryBandDto;
import com.acme.salary.employee.entity.Employee;
import com.acme.salary.employee.repository.EmployeeRepository;
import com.acme.salary.employee.repository.SalaryRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRecordRepository salaryRecordRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SalaryRecordRepository salaryRecordRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRecordRepository = salaryRecordRepository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDto> searchEmployees(String search, String country, String department, Pageable pageable) {
        return employeeRepository.findByFilters(search, country, department, pageable)
                .map(this::toDto);
    }

    @Transactional
    public EmployeeDto createEmployee(CreateEmployeeRequest request) {
        String code = request.employeeCode() == null || request.employeeCode().isBlank()
                ? "EMP-" + System.currentTimeMillis()
                : request.employeeCode();

        if (employeeRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Employee with email already exists");
        }

        Employee employee = new Employee();
        employee.setEmployeeCode(code);
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setCountry(request.country());
        employee.setDepartment(request.department());
        employee.setDesignation(request.designation());
        employee.setJoiningDate(request.joiningDate());
        employee.setCurrency(request.currency());

        Employee saved = employeeRepository.save(employee);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return toDto(employee);
    }

    @Transactional(readOnly = true)
    public DashboardSummaryDto getDashboardSummary() {
        long totalEmployees = employeeRepository.count();
        double annualPayroll = salaryRecordRepository.getTotalAnnualPayroll() == null ? 0.0 : salaryRecordRepository.getTotalAnnualPayroll();
        double averageSalary = salaryRecordRepository.getAverageAnnualSalary() == null ? 0.0 : salaryRecordRepository.getAverageAnnualSalary();
        double salaryVariance = salaryRecordRepository.getVariancePercent() == null ? 0.0 : salaryRecordRepository.getVariancePercent();

        List<DepartmentSummaryDto> departmentBreakdown = salaryRecordRepository.getDepartmentPayrollSummary().stream()
                .map(row -> new DepartmentSummaryDto(
                        (String) row[0],
                        ((Number) row[1]).longValue(),
                        ((Number) row[2]).doubleValue(),
                        ((Number) row[3]).doubleValue()))
                .toList();

        List<SalaryBandDto> salaryBands = salaryRecordRepository.getSalaryBandSummary().stream()
                .map(row -> new SalaryBandDto(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .toList();

        return new DashboardSummaryDto(totalEmployees, annualPayroll, averageSalary, salaryVariance, departmentBreakdown, salaryBands);
    }

    private EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getCountry(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getJoiningDate(),
                employee.getCurrency()
        );
    }
}
