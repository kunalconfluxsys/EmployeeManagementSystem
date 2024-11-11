package com.example.employee_sytem.mapper;

import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.Department;


/**
 * Mapper class for converting between Employee and EmployeeDto.
 * <p>
 * This class provides utility methods to map an Employee entity to its corresponding
 * EmployeeDto and vice versa, facilitating the conversion of data between the
 * entity and DTO layers of the application.
 * </p>
 */
public class EmployeeMapper {

    /**
     * Converts an Employee entity to an EmployeeDto.
     *
     * <p>
     * This method takes an Employee entity object and maps it to an EmployeeDto object,
     * which is used for transferring employee data in API responses or other service layers.
     * The department information is also included as the department's ID.
     * </p>
     *
     * @param employee the Employee entity to be converted
     * @return the corresponding EmployeeDto or null if the employee is null
     */
    public static EmployeeDto mapToEmployeeDto(Employee employee) {

        if (employee == null) {
            return null; // Handle null employee input
        }

        // Create and return the EmployeeDto based on the employee entity data
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null // Map department ID
        );
    }

    /**
     * Converts an EmployeeDto to an Employee entity.
     *
     * <p>
     * This method takes an EmployeeDto object (which is typically used for data transfer)
     * and maps it to an Employee entity object, which is suitable for database operations
     * (e.g., saving or updating an employee record).
     * </p>
     *
     * @param employeeDto the EmployeeDto to be converted
     * @return the corresponding Employee entity or null if the employeeDto is null
     */
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null; // Handle null employeeDto input
        }

        // Create a new Employee entity object from the employeeDto data
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );

        // Set the department for the employee based on departmentId if present
        if (employeeDto.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(employeeDto.getDepartmentId()); // Only set the ID of the department
            employee.setDepartment(department); // Associate department with the employee entity
        }

        return employee;
    }
}
