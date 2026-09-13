package com.acme.salary.employee.controller;

import com.acme.salary.employee.dto.EmployeeDto;
import com.acme.salary.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Search and list employees")
    public ResponseEntity<Page<EmployeeDto>> searchEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String department,
            Pageable pageable) {
        return ResponseEntity.ok(employeeService.searchEmployees(search, country, department, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }
}
