package com.example.employee_sytem.mapper;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.entity.Department;
/**
 * Mapper class for converting between Department and DepartmentDto.
 * <p>
 * This class provides methods to map a Department entity to its corresponding
 * DepartmentDto and vice versa, making it easier to convert between layers
 * in the application (i.e., entity layer to DTO layer and vice versa).
 * </p>
 */
public class DepartmentMapper {

    /**
     * Converts a Department entity to a DepartmentDto.
     * <p>
     * This method takes a Department entity object and maps it to a DepartmentDto object,
     * which is used for transferring department data in API responses or other service layers.
     * </p>
     *
     * @param department the Department entity to be converted
     * @return the corresponding DepartmentDto or null if the input is null
     */
    public static DepartmentDto mapToDepartmentDto(Department department) {
        if (department == null) {
            return null; // Handle null department input
        }

        // Map the Department entity to a DepartmentDto object
        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }

    /**
     * Converts a DepartmentDto to a Department entity.
     * <p>
     * This method takes a DepartmentDto object (which is typically used for data transfer)
     * and maps it to a Department entity object, which is suitable for database operations
     * (e.g., saving or updating a department record).
     * </p>
     *
     * @param departmentDto the DepartmentDto to be converted
     * @return the corresponding Department entity or null if the input is null
     */
    public static Department mapToDepartment(DepartmentDto departmentDto) {
        if (departmentDto == null) {
            return null; // Handle null departmentDto input
        }

        // Create and return a new Department entity object
        return new Department(
                departmentDto.getId(),
                departmentDto.getName(),
                departmentDto.getDescription() // Map the description field as well
        );
    }

}
