package com.example.employee_sytem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Data Transfer Object (DTO) for Department.
 * <p>
 * This class is used to transfer department data between layers of the application, such as
 * from the service layer to the controller or from the controller to the client.
 * It encapsulates the department's attributes in a simplified format.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    /**
     * The unique identifier for the department.
     * <p>
     * This field represents the department's unique identifier in the system.
     * It is used to fetch, update, or delete department records.
     * </p>
     */
    private Long id;

    /**
     * The name of the department.
     * <p>
     * This field stores the name of the department and is typically used to display
     * the department in user interfaces or reports.
     * </p>
     */
    private String name;

    /**
     * The description of the department.
     * <p>
     * This field provides additional information about the department. It may describe
     * the department's role or function within the organization.
     * </p>
     */
    private String description;
}




