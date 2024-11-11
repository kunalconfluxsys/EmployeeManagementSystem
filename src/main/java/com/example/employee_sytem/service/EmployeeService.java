package com.example.employee_sytem.service;
import com.example.employee_sytem.dto.EmployeeDto;
import java.util.List;
/**
 * Service interface for managing employee operations.
 * <p>
 * This interface defines the methods for creating, retrieving, updating, and deleting
 * employee data. It also provides methods for handling employee status, assignments,
 * and search operations.
 * </p>

 */
public interface EmployeeService {

    /**
     * Creates a new employee.
     *
     * @param employeeDto the employee data to be created
     * @return the created EmployeeDto with its generated ID
     */
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the EmployeeDto corresponding to the given ID, or null if not found
     */
    EmployeeDto getEmployeeById(Long employeeId);

    /**
     * Retrieves all employees.
     *
     * @return a list of EmployeeDto representing all employees
     */
    List<EmployeeDto> getAllEmployees();

    /**
     * Updates an existing employee's information.
     *
     * @param employeeId the ID of the employee to update
     * @param updatedEmployee the new employee data to update
     * @return the updated EmployeeDto
     */
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     */
    void deleteEmployee(Long employeeId);

    /**
     * Assigns an employee to a department.
     *
     * @param employeeId the ID of the employee to assign
     * @param departmentId the ID of the department to assign the employee to
     */
    void assignEmployeeToDepartment(Long employeeId, Long departmentId);

    /**
     * Searches for employees by name, department, and active status.
     *
     * @param name the name of the employee to search for (can be partial)
     * @param departmentId the department ID to filter employees by (can be null)
     * @param active the active status to filter employees by (can be null)
     * @return a list of EmployeeDto matching the search criteria
     */
    List<EmployeeDto> searchEmployees(String name, Long departmentId, Boolean active);

    /**
     * Retrieves a list of employees by their IDs.
     *
     * @param employeeIds the list of employee IDs to retrieve
     * @return a list of EmployeeDto corresponding to the provided IDs
     */
    List<EmployeeDto> getEmployeesByIds(List<Long> employeeIds);



}


