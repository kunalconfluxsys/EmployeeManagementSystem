package com.example.employee_sytem.repository;

import com.example.employee_sytem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Employee} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and custom query methods for Employee entities.
 * </p>
 *
 * @author Kunal Kale
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Additional query methods can be defined here
    @Query("SELECT e FROM Employee e WHERE "
            + "(:name IS NULL OR e.firstName LIKE %:name% OR e.lastName LIKE %:name%) AND "
            + "(:departmentId IS NULL OR e.department.id = :departmentId) AND "
            + "(:active IS NULL OR e.active = :active)")
    List<Employee> searchEmployees(
            @Param("name") String name,
            @Param("departmentId") Long departmentId,
            @Param("active") Boolean active
    );

    List<Employee> findByManagerId(Long managerId);

    List<Employee> findByManager(Employee manager);
}


