package com.acme.salary.employee.service;

import com.acme.salary.common.exception.ResourceNotFoundException;
import com.acme.salary.employee.dto.EmployeeDto;
import com.acme.salary.employee.entity.Employee;
import com.acme.salary.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDto> searchEmployees(String search, String country, String department, Pageable pageable) {
        return employeeRepository.findByFilters(search, country, department, pageable)
                .map(this::toDto);
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return toDto(employee);
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
