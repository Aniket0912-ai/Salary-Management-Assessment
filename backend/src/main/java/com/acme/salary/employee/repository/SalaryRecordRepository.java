package com.acme.salary.employee.repository;

import com.acme.salary.employee.entity.SalaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRecordRepository extends JpaRepository<SalaryRecord, Long> {

    @Query(value = "SELECT COALESCE(SUM(annual_salary), 0) FROM salary_records", nativeQuery = true)
    Double getTotalAnnualPayroll();

    @Query(value = "SELECT COALESCE(AVG(annual_salary), 0) FROM salary_records", nativeQuery = true)
    Double getAverageAnnualSalary();

    @Query(value = "SELECT COALESCE((STDDEV(annual_salary) / NULLIF(AVG(annual_salary), 0)) * 100, 0) FROM salary_records", nativeQuery = true)
    Double getVariancePercent();

    @Query(value = "SELECT e.department, COUNT(e.id), COALESCE(SUM(sr.annual_salary), 0), " +
            "COALESCE((SUM(sr.annual_salary) * 100.0) / NULLIF((SELECT SUM(annual_salary) FROM salary_records), 0), 0) " +
            "FROM employees e LEFT JOIN salary_records sr ON sr.employee_id = e.id " +
            "GROUP BY e.department ORDER BY SUM(sr.annual_salary) DESC", nativeQuery = true)
    List<Object[]> getDepartmentPayrollSummary();

    @Query(value = "SELECT CASE " +
            "WHEN annual_salary < 60000 THEN '0-60K' " +
            "WHEN annual_salary < 100000 THEN '60-100K' " +
            "WHEN annual_salary < 150000 THEN '100-150K' " +
            "ELSE '150K+' END, COUNT(*) " +
            "FROM salary_records GROUP BY CASE " +
            "WHEN annual_salary < 60000 THEN '0-60K' " +
            "WHEN annual_salary < 100000 THEN '60-100K' " +
            "WHEN annual_salary < 150000 THEN '100-150K' " +
            "ELSE '150K+' END ORDER BY 1", nativeQuery = true)
    List<Object[]> getSalaryBandSummary();
}
