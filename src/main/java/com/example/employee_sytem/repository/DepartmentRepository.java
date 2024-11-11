package com.example.employee_sytem.repository;
import org.springframework.stereotype.Repository;
import com.example.employee_sytem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link Department} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * such as saving, deleting, and finding departments. It also allows for defining
 * custom query methods if needed in the future.
 * </p>
 * <p>
 * The {@link JpaRepository} provides default methods for common database operations like:
 * <ul>
 *   <li>findAll()</li>
 *   <li>findById()</li>
 *   <li>save()</li>
 *   <li>deleteById()</li>
 * </ul>
 * </p>
 * <p>
 * Custom query methods can be added here if more specific or complex queries are required
 * beyond the standard CRUD operations provided by {@link JpaRepository}.
 * </p>
 *

 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Additional query methods can be defined here if needed
    }


