package com.example.employee_sytem.service.Impl;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import com.example.employee_sytem.service.DepartmentService;
import com.example.employee_sytem.service.EmployeeService;
import com.example.employee_sytem.service.LeaveRequestServiceInterface;
import com.example.employee_sytem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ReportService interface to handle the generation of reports.
 * This service fetches employee, department, and leave summary details for generating reports.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmployeeService employeeService;  // Service for handling employee data

    @Autowired
    private DepartmentService departmentService;  // Service for handling department data

    @Autowired
    private LeaveRequestServiceInterface leaveRequestService;  // Service for handling leave request data

    /**
     * Fetches the details of all employees for generating a report.
     *
     * @return a list of EmployeeDto objects representing the employee details.
     */
    @Override
    public List<EmployeeDto> getEmployeeDetailsForReport() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();  // Get all employees
        return employees.stream().map(employee -> {
            // Convert Employee entity to EmployeeDTO
            return new EmployeeDto(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getDepartmentId()
            );
        }).collect(Collectors.toList());
    }

    /**
     * Fetches department details for a given department ID to include in the report.
     *
     * @param departmentId the ID of the department whose details are to be retrieved.
     * @return a DepartmentDto object containing the department details, or null if no department is found.
     */
    @Override
    public DepartmentDto getDepartmentDetails(Long departmentId) {
        DepartmentDto department = departmentService.getDepartmentById(departmentId);
        if (department != null) {
            return new DepartmentDto(department.getId(), department.getName(), department.getDescription());
        }
        return null;  // Return null if no department found
    }

    /**
     * Fetches the leave summary for an employee, including details of their total leave,
     * taken leave, and remaining leave.
     *
     * @param employeeId the ID of the employee whose leave summary is to be fetched.
     * @return a LeaveSummaryDTO object representing the leave summary of the employee.
     */
    @Override
    public LeaveSummaryDTO getLeaveSummary(Long employeeId) {
        // Fetch the leave summary for the given employee (from leaveRequestService)
        return leaveRequestService.getLeaveSummary(employeeId);
    }

}
