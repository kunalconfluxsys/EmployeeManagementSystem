package com.example.employee_sytem.service;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import java.util.List;

/**
 * Service interface for generating various reports in the employee system.
 * <p>
 * This interface provides methods to retrieve employee details, department details,
 * and leave summaries, which can be used to generate reports for administrative purposes.
 * </p>
 */
public interface ReportService {

    /**
     * Fetches the details of all employees for reporting purposes.
     * <p>
     * This method retrieves a list of EmployeeDto objects that contain employee details,
     * such as name, email, department, etc., which can be used to generate a report of all employees.
     * </p>
     *
     * @return a list of EmployeeDto representing the details of all employees
     */
    List<EmployeeDto> getEmployeeDetailsForReport();  // Fetch employee details for report

    /**
     * Fetches the details of a specific department for reporting purposes.
     * <p>
     * This method retrieves a DepartmentDto that contains information such as the department's name,
     * description, and other related details, which can be included in a department report.
     * </p>
     *
     * @param departmentId the ID of the department whose details are to be fetched
     * @return the DepartmentDto representing the details of the department
     */
    DepartmentDto getDepartmentDetails(Long departmentId);  // Fetch department details for report

    /**
     * Fetches the leave summary for an employee for reporting purposes.
     * <p>
     * This method retrieves the leave summary for a specific employee, including total leave days,
     * leave days taken, and remaining leave days, which can be included in a report to track employee leave.
     * </p>
     *
     * @param employeeId the ID of the employee whose leave summary is to be fetched
     * @return the LeaveSummaryDTO containing the employee's leave details
     */
    LeaveSummaryDTO getLeaveSummary(Long employeeId);  // Fetch leave summary for report
}