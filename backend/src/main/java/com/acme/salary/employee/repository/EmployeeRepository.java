package com.acme.salary.employee.repository;

import com.acme.salary.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT e FROM Employee e WHERE " +
            "(:search IS NULL OR :search = '' OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:country IS NULL OR :country = '' OR e.country = :country) " +
            "AND (:department IS NULL OR :department = '' OR e.department = :department)")
    Page<Employee> findByFilters(@Param("search") String search,
                                 @Param("country") String country,
                                 @Param("department") String department,
                                 Pageable pageable);

    boolean existsByEmail(String email);
}
