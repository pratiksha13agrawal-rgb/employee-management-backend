package com.example.employee_management.repository;
import com.example.employee_management.model.Employee;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE " +
    "LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
    "LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
    "LOWER(e.department) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Employee> search(@Param("query") String query);
}
