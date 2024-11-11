package com.example.employee_sytem.controller;

import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing departments.
 * <p>
 * This controller exposes endpoints for creating, retrieving, updating, and deleting departments
 * in the system. It allows for CRUD operations on departments via a RESTful API.
 * </p>
 *
 * @author Kunal Kale
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Creates a new department.
     *
     * @param departmentDto the department data to be created
     * @return ResponseEntity containing the created DepartmentDto and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param departmentId the ID of the department to retrieve
     * @return ResponseEntity containing the DepartmentDto and HTTP status 200 (OK)
     */
    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId) {
        DepartmentDto departmentDto = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(departmentDto);
    }

    /**
     * Retrieves all departments.
     *
     * @return ResponseEntity containing a list of DepartmentDto and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Updates an existing department.
     *
     * @param departmentId      the ID of the department to update
     * @param updatedDepartment the department data to update
     * @return ResponseEntity containing the updated DepartmentDto and HTTP status 200 (OK)
     */
    @PutMapping("{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentId,
                                                          @RequestBody DepartmentDto updatedDepartment) {
        DepartmentDto departmentDto = departmentService.updateDepartment(departmentId, updatedDepartment);
        return ResponseEntity.ok(departmentDto);
    }

    /**
     * Deletes a department by its ID.
     *
     * @param departmentId the ID of the department to delete
     * @return ResponseEntity containing a confirmation message and HTTP status 200 (OK)
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department Deleted Successfully");
    }

}
