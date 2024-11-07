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

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final EmployeeRepository employeeRepository;
    private  final DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDepartmentDto(department);

    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(updatedDepartment.getName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDto(savedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
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
