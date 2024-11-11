package com.example.employee_sytem.service.Impl;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.entity.Department;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.exception.ResourceNotFoundException;
import com.example.employee_sytem.mapper.EmployeeMapper;
import com.example.employee_sytem.repository.DepartmentRepository;
import com.example.employee_sytem.repository.EmployeeRepository;
import com.example.employee_sytem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the EmployeeService interface.
 * <p>
 * This service class provides methods for managing employee operations,
 * including creating, retrieving, updating, and deleting employee records.
 * It also allows employees to be assigned to departments, searched based on different criteria,
 * and fetched by their IDs.
 * </p>
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * Creates a new employee.
     * <p>
     * This method maps the provided EmployeeDto to an Employee entity,
     * saves it to the database, and returns the corresponding EmployeeDto
     * with the generated ID.
     * </p>
     *
     * @param employeeDto the employee data to be created
     * @return the created EmployeeDto with its generated ID
     */
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    /**
     * Retrieves an employee by their ID.
     * <p>
     * This method fetches an employee from the database by their ID and
     * maps it to an EmployeeDto. If no employee is found, it throws a
     * ResourceNotFoundException.
     * </p>
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the EmployeeDto corresponding to the given employee
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    /**
     * Retrieves all employees.
     * <p>
     * This method fetches all employee records from the database and returns
     * them as a list of EmployeeDto objects.
     * </p>
     *
     * @return a list of EmployeeDto representing all employees
     */
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing employee's information.
     * <p>
     * This method updates the details of an existing employee based on the
     * provided EmployeeDto, saves the updated entity, and returns the
     * updated EmployeeDto.
     * </p>
     *
     * @param employeeId      the ID of the employee to update
     * @param updatedEmployee the updated employee data
     * @return the updated EmployeeDto
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    /**
     * Deletes an employee by their ID.
     * <p>
     * This method deletes an employee from the database by their ID. If no
     * employee is found, it throws a ResourceNotFoundException.
     * </p>
     *
     * @param employeeId the ID of the employee to delete
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Assigns an employee to a department.
     * <p>
     * This method assigns an employee to a specific department. It first
     * retrieves the employee and department by their respective IDs,
     * then updates the employee's department and saves the employee.
     * </p>
     *
     * @param employeeId   the ID of the employee to assign
     * @param departmentId the ID of the department to assign the employee to
     * @throws ResourceNotFoundException if no employee or department exists with the given ID
     */
    @Override
    public void assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    /**
     * Searches for employees based on various criteria such as name, department, and active status.
     * <p>
     * This method allows searching employees by their name, department, and active status.
     * It returns a list of EmployeeDto objects matching the criteria.
     * </p>
     *
     * @param name        the name to search for (can be partial)
     * @param departmentId the ID of the department to filter by
     * @param active      the active status to filter by (true for active, false for inactive)
     * @return a list of EmployeeDto matching the search criteria
     */
    @Override
    public List<EmployeeDto> searchEmployees(String name, Long departmentId, Boolean active) {
        List<Employee> employees = employeeRepository.searchEmployees(name, departmentId, active);
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of employees by their IDs.
     * <p>
     * This method fetches employees based on a list of provided IDs and
     * returns the corresponding EmployeeDto objects. If no employees are found,
     * a ResourceNotFoundException is thrown.
     * </p>
     *
     * @param employeeIds the list of employee IDs to fetch
     * @return a list of EmployeeDto objects for the specified employee IDs
     * @throws ResourceNotFoundException if no employees are found for the provided IDs
     */
    @Override
    public List<EmployeeDto> getEmployeesByIds(List<Long> employeeIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found for the provided IDs.");
        }
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }
}
