package com.example.employee_sytem.service;
import com.example.employee_sytem.dto.DepartmentDto;
import java.util.List;

/**
 * Service interface for managing department operations.
 * <p>
 * This interface defines the methods for creating, retrieving, updating, and deleting
 * department data. It also includes methods for fetching departments based on employee associations.
 * </p>
 */
public interface DepartmentService {

    /**
     * Creates a new department.
     *
     * @param departmentDto the department data to be created
     * @return the created DepartmentDto with its generated ID
     */
    DepartmentDto createDepartment(DepartmentDto departmentDto);

    /**
     * Retrieves a department by its ID.
     *
     * @param departmentId the ID of the department to retrieve
     * @return the DepartmentDto corresponding to the given ID, or null if not found
     */
    DepartmentDto getDepartmentById(Long departmentId);

    /**
     * Retrieves all departments.
     *
     * @return a list of DepartmentDto representing all departments
     */
    List<DepartmentDto> getAllDepartments();

    /**
     * Updates an existing department's information.
     *
     * @param departmentId the ID of the department to update
     * @param updatedDepartment the new department data to update
     * @return the updated DepartmentDto
     */
    DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment);

    /**
     * Deletes a department by its ID.
     *
     * @param departmentId the ID of the department to delete
     */
    void deleteDepartment(Long departmentId);

    /**
     * Retrieves the department for a specific employee.
     * <p>
     * This method will return the department to which the specified employee is assigned.
     * </p>
     *
     * @param employeeId the ID of the employee whose department is to be fetched
     * @return the DepartmentDto of the employee's department, or null if not found
     */
    DepartmentDto getDepartmentForEmployee(Long employeeId);

}
