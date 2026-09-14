package com.acme.salary;

import com.acme.salary.employee.entity.Employee;
import com.acme.salary.employee.entity.SalaryRecord;
import com.acme.salary.employee.repository.EmployeeRepository;
import com.acme.salary.employee.repository.SalaryRecordRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final EmployeeRepository employeeRepository;
    private final SalaryRecordRepository salaryRecordRepository;

    public DataSeeder(EmployeeRepository employeeRepository, SalaryRecordRepository salaryRecordRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRecordRepository = salaryRecordRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (employeeRepository.count() > 0) {
            return;
        }

        List<Employee> employees = new ArrayList<>();
        List<SalaryRecord> salaryRecords = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {
            Employee employee = new Employee();
            String[] firstNames = {"Ava", "Noah", "Mia", "Ethan", "Olivia", "Liam", "Sophia", "Mason", "Charlotte", "Lucas"};
            String[] lastNames = {"Patel", "Nguyen", "Smith", "Muller", "Brown", "Lee", "Singh", "Kim", "Rossi", "Davis"};
            String[] countries = {"USA", "India", "Germany", "United Kingdom", "Canada"};
            String[] currencies = {"USD", "INR", "EUR", "GBP", "CAD"};
            String[] departments = {"Engineering", "Finance", "Operations", "Sales", "People Ops"};
            String[] designations = {"Senior Engineer", "Manager", "Analyst", "Director", "Associate"};

            String firstName = firstNames[(i - 1) % firstNames.length];
            String lastName = lastNames[(i * 3) % lastNames.length];
            String country = countries[(i - 1) % countries.length];
            String currency = currencies[(i - 1) % currencies.length];
            String department = departments[(i + 2) % departments.length];
            String designation = designations[(i - 1) % designations.length];
            String employeeCode = String.format("ACME-%05d", i);
            String email = String.format("%s.%s%d@acme.com", firstName.toLowerCase(), lastName.toLowerCase(), i);
            LocalDate joiningDate = LocalDate.now().minusDays((i % 1825) + 1);
            BigDecimal annualSalary = BigDecimal.valueOf(50000 + ((i * 791L) % 140000L));

            employee.setEmployeeCode(employeeCode);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setCountry(country);
            employee.setDepartment(department);
            employee.setDesignation(designation);
            employee.setJoiningDate(joiningDate);
            employee.setCurrency(currency);
            employees.add(employee);

            SalaryRecord salaryRecord = new SalaryRecord();
            salaryRecord.setEmployeeId(null);
            salaryRecord.setAnnualSalary(annualSalary);
            salaryRecord.setCurrency(currency);
            salaryRecord.setEffectiveFrom(LocalDateTime.now());
            salaryRecord.setCreatedBy("system-seed");
            salaryRecords.add(salaryRecord);
        }

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);

        for (int i = 0; i < savedEmployees.size(); i++) {
            SalaryRecord record = salaryRecords.get(i);
            record.setEmployeeId(savedEmployees.get(i).getId());
        }

        salaryRecordRepository.saveAll(salaryRecords);
    }
}
