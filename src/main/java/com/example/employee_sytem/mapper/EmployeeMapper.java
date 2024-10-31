package com.example.employee_sytem.mapper;

import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.Department;


/**
 * Mapper class for converting between Employee and EmployeeDto.
 * <p>
 * This class provides methods to map an Employee entity to its corresponding
 * EmployeeDto and vice versa, facilitating the conversion of data between
 * different layers of the application.
 * </p>
 */

public class EmployeeMapper {
    /**
     * Converts an Employee entity to an EmployeeDto.
     *
     * @param employee the Employee entity to be converted
     * @return the corresponding EmployeeDto
     */
    public static EmployeeDto mapToEmployeeDto(Employee employee) {

        if (employee == null) {
            return null; // Handle null employee
        }

      return new EmployeeDto(

                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null // Get department ID

        );



    }
    /**
     * Converts an EmployeeDto to an Employee entity.
     *
     * @param employeeDto the EmployeeDto to be converted
     * @return the corresponding Employee entity
     */
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
                 if (employeeDto == null) {
                     return null; // Handle null employeeDto
                 }
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );

        // Set department if departmentId is provided
        if (employeeDto.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(employeeDto.getDepartmentId()); // Only set the ID for now
            employee.setDepartment(department); // Associate department with the employee
        }

        return employee;
    }
}
