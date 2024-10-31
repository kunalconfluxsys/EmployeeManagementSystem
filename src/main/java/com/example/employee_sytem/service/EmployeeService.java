package com.example.employee_sytem.service;

import com.example.employee_sytem.dto.EmployeeDto;


import java.util.List;
import java.io.*;

        /**
 * Service interface for managing employee operations.
 * <p>
 * This interface defines the methods for creating, retrieving, updating, and deleting
 * employee data.
 * </p>
 *
 * @author Kunal Kale
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

     void assignEmployeeToDepartment(Long employeeId, Long departmentId);
     List<EmployeeDto> searchEmployees(String name, Long departmentId, Boolean active);
}
