package com.example.employee_sytem.service.Impl;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.entity.Department;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.mapper.DepartmentMapper;
import com.example.employee_sytem.repository.DepartmentRepository;
import com.example.employee_sytem.repository.EmployeeRepository;
import com.example.employee_sytem.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.employee_sytem.mapper.DepartmentMapper.mapToDepartmentDto;

/**
 * Implementation of the DepartmentService interface, providing methods to manage departments.
 */
@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * Creates a new department.
     *
     * @param departmentDto the DepartmentDto object containing the details of the department to be created.
     * @return the created DepartmentDto object.
     * @throws RuntimeException if the department creation fails.
     */
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());

        // Handle description field (if provided, else set default)
        if (departmentDto.getDescription() != null && !departmentDto.getDescription().isEmpty()) {
            department.setDescription(departmentDto.getDescription());
        } else {
            // Set a default description if none provided
            department.setDescription("No description provided");
        }

        // Save the department
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDto(savedDepartment);
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param departmentId the ID of the department to retrieve.
     * @return the DepartmentDto object representing the department.
     * @throws RuntimeException if the department with the given ID is not found.
     */
    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDepartmentDto(department);
    }

    /**
     * Retrieves all departments.
     *
     * @return a list of DepartmentDto objects representing all departments.
     */
    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates the details of an existing department.
     *
     * @param departmentId the ID of the department to be updated.
     * @param updatedDepartment the updated DepartmentDto containing the new details.
     * @return the updated DepartmentDto object.
     * @throws RuntimeException if the department with the given ID is not found.
     */
    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Update the name and description (if provided)
        department.setName(updatedDepartment.getName());

        // Update description if it's provided (can handle null/empty cases)
        if (updatedDepartment.getDescription() != null && !updatedDepartment.getDescription().isEmpty()) {
            department.setDescription(updatedDepartment.getDescription());
        }

        // Save updated department
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDto(savedDepartment);
    }

    /**
     * Deletes a department by its ID.
     *
     * @param departmentId the ID of the department to be deleted.
     * @throws RuntimeException if the department with the given ID is not found.
     */
    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    /**
     * Retrieves the department information for a specific employee.
     *
     * @param employeeId the ID of the employee whose department information is to be retrieved.
     * @return a DepartmentDto object representing the department of the employee, or null if the employee does not have a department.
     */
    @Override
    public DepartmentDto getDepartmentForEmployee(Long employeeId) {
        // Fetch employee by ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElse(null); // Handle case where employee doesn't exist
        if (employee != null && employee.getDepartment() != null) {
            // Map the department entity to DTO
            return DepartmentMapper.mapToDepartmentDto(employee.getDepartment());
        }
        return null; // If employee or department is not found, return null
    }
}
