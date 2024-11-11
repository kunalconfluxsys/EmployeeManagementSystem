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
 * such as saving, deleting, and finding employees. It also includes custom query methods
 * for searching employees based on various parameters and retrieving employees by their manager.
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
 * Custom query methods are defined using JPQL (Java Persistence Query Language) in this repository.
 * </p>
 *

 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Custom query to search for employees based on name, department, and active status.
     * <p>
     * This method allows searching for employees with the following optional parameters:
     * <ul>
     *   <li><b>name</b>: Can match the employee's first or last name (partial matches allowed).</li>
     *   <li><b>departmentId</b>: The department ID to filter employees who belong to a specific department.</li>
     *   <li><b>active</b>: Filters employees based on their active status (true or false).</li>
     * </ul>
     * </p>
     *
     * @param name The name to search for. Can be a partial match for first or last name. May be null.
     * @param departmentId The ID of the department to filter employees by. Can be null to ignore this filter.
     * @param active The active status to filter by (true or false). Can be null to ignore this filter.
     * @return A list of employees matching the search criteria.
     */
    @Query("SELECT e FROM Employee e WHERE "
            + "(:name IS NULL OR e.firstName LIKE %:name% OR e.lastName LIKE %:name%) AND "
            + "(:departmentId IS NULL OR e.department.id = :departmentId) AND "
            + "(:active IS NULL OR e.active = :active)")
    List<Employee> searchEmployees(
            @Param("name") String name,
            @Param("departmentId") Long departmentId,
            @Param("active") Boolean active
    );

    /**
     * Find all employees who report to a specific manager by their manager's ID.
     * <p>
     * This method retrieves a list of employees who are managed by a specific manager, identified by their manager's ID.
     * </p>
     *
     * @param managerId The ID of the manager whose employees are to be retrieved.
     * @return A list of employees who report to the manager with the given ID.
     */
    List<Employee> findByManagerId(Long managerId);

    /**
     * Find all employees who report to a specific manager by the manager's {@link Employee} entity.
     * <p>
     * This method retrieves a list of employees who are managed by a specific manager, identified by the {@link Employee}
     * object representing the manager. It is an alternative to using the manager's ID.
     * </p>
     *
     * @param manager The {@link Employee} object representing the manager.
     * @return A list of employees who report to the given manager.
     */
    List<Employee> findByManager(Employee manager);

}


