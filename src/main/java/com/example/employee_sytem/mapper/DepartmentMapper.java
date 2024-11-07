package com.example.employee_sytem.mapper;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.entity.Department;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Department and DepartmentDto.
 */
public class DepartmentMapper {
    /**
         * Converts a Department entity to a DepartmentDto.
         *
         * @param department the Department entity to be converted
         * @return the corresponding DepartmentDto or null if the input is null
         */
        public static DepartmentDto mapToDepartmentDto(Department department) {
            if (department == null) {
                return null; // Handle null department
            }

            return new DepartmentDto(
                    department.getId(),
                    department.getName(),
                    department.getDescription()
            );
        }

        /**
         * Converts a DepartmentDto to a Department entity.
         *
         * @param departmentDto the DepartmentDto to be converted
         * @return the corresponding Department entity or null if the input is null
         */
        public static Department mapToDepartment(DepartmentDto departmentDto) {
            if (departmentDto == null) {
                return null; // Handle null departmentDto
            }

            return new Department(
                    departmentDto.getId(),
                    departmentDto.getName(),
                    departmentDto.getDescription()  // Include description
            );
        }
}
