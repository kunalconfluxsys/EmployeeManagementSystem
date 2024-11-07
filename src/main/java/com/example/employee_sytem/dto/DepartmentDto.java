package com.example.employee_sytem.dto;

import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    /**
     * Data Transfer Object for Department.
     * <p>
     * This class is used to transfer department data between layers of the application.
     * </p>
     */
    private Long id;
    private String name;
    private String description;





}
