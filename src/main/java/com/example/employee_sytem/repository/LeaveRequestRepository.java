package com.example.employee_sytem.repository;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for managing {@link LeaveRequest} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and custom query methods for {@link LeaveRequest} entities.
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
 * Custom query methods are defined in this repository to fetch leave requests for a specific employee
 * or based on their approval status, and to count the number of requests with a given status.
 * </p>
 *
 */
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    /**
     * Find all leave requests for a specific employee.
     * <p>
     * This method retrieves all leave requests for the given employee, regardless of their status.
     * </p>
     *
     * @param employee The employee whose leave requests are to be retrieved.
     * @return A list of {@link LeaveRequest} entities for the given employee.
     */
    List<LeaveRequest> findByEmployee(Employee employee);

    /**
     * Find all leave requests for a specific employee with a specific status (e.g., APPROVED).
     * <p>
     * This method retrieves all leave requests for the given employee, but filters the results
     * based on the leave request's status. For example, it can return only the requests that are
     * approved or denied.
     * </p>
     *
     * @param employee The employee whose leave requests are to be retrieved.
     * @param status The status of the leave request (e.g., "APPROVED", "DENIED").
     * @return A list of {@link LeaveRequest} entities for the given employee with the specified status.
     */
    List<LeaveRequest> findByEmployeeAndStatus(Employee employee, String status);

    /**
     * Count the number of leave requests for a specific employee with a specific status.
     * <p>
     * This method counts how many leave requests the given employee has with the specified status.
     * For example, it can be used to count the number of approved leave requests for an employee.
     * </p>
     *
     * @param employee The employee whose leave requests are to be counted.
     * @param status The status of the leave request (e.g., "APPROVED", "DENIED").
     * @return The count of {@link LeaveRequest} entities for the given employee with the specified status.
     */
    long countByEmployeeAndStatus(Employee employee, String status);
}
