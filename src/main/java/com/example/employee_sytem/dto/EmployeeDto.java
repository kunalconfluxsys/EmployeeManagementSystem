package com.example.employee_sytem.dto;


import lombok.*;


/**
 * Data Transfer Object (DTO) for Employee.
 * <p>
 * This class represents the employee data to be transferred between
 * the client and the server, encapsulating the employee's attributes.
 * It is used to exchange employee-related data in a simplified form,
 * without exposing the internal entity details.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    /**
     * The unique identifier for the employee.
     * This field is used to identify an employee uniquely across the system.
     * It maps to the "id" field of the Employee entity.
     */
    private Long id;

    /**
     * The first name of the employee.
     * This field stores the first name of the employee and is used to identify the employee in various reports and communications.
     */
    private String firstName;

    /**
     * The last name of the employee.
     * This field stores the last name of the employee and is used to identify the employee in various reports and communications.
     */
    private String lastName;

    /**
     * The email address of the employee.
     * This field stores the employee's email address, which is expected to be unique for each employee.
     * It is typically used for communication and login purposes.
     */
    private String email;

    /**
     * The department ID to which the employee belongs.
     * This field is used to specify which department the employee is associated with.
     * It is usually used for linking the employee to their department in the backend system.
     */
    private Long departmentId;


}
