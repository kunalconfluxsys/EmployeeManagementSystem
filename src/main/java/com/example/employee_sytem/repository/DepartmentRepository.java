package com.example.employee_sytem.repository;
import org.springframework.stereotype.Repository;
import com.example.employee_sytem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Department} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and custom query methods for Department entities.
 * </p>
 *
 * @author Kunal Kale
 */



    @Repository
    public interface DepartmentRepository extends JpaRepository<Department, Long> {

        // Additional query methods can be defined here if needed
    }


