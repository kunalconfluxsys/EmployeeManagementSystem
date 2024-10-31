package com.example.employee_sytem.service.Impl;

import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.entity.Department;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.exception.ResourceNotFoundException;
import com.example.employee_sytem.mapper.EmployeeMapper;
import com.example.employee_sytem.repository.DepartmentRepository;
import com.example.employee_sytem.repository.EmployeeRepository;
import com.example.employee_sytem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;




/**
 * Implementation of the EmployeeService interface.
 * <p>
 * This service class provides methods for managing employee operations,
 * including creating, retrieving, updating, and deleting employee records.
 * </p>
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private  final DepartmentRepository departmentRepository;
    /**
     * Creates a new employee.
     *
     * @param employeeDto the employee data to be created
     * @return the created EmployeeDto
     */


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the EmployeeDto corresponding to the employee
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee is not exist with given id:" + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of EmployeeDto representing all employees
     */
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing employee.
     *
     * @param employeeId      the ID of the employee to update
     * @param updatedEmployee the updated employee data
     * @return the updated EmployeeDto
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with the given id: " + employeeId)
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);


    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exists with given id:" + employeeId)
        );
        employeeRepository.deleteById(employeeId);

    }

    @Override
    public void assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        employee.setDepartment(department);
        employeeRepository.save(employee);

    }

    @Override
    public List<EmployeeDto> searchEmployees(String name, Long departmentId, Boolean active) {


        List<Employee> employees = employeeRepository.searchEmployees(name, departmentId, active);
               return employees.stream()

                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());

    }

    private DepartmentDto mapToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;

    }

}
