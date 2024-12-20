package com.example.employee_sytem.controller;
import com.example.employee_sytem.dto.DepartmentDto;
import com.example.employee_sytem.dto.EmployeeDto;
import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import com.example.employee_sytem.service.DepartmentService;
import com.example.employee_sytem.service.DocumentService;
import com.example.employee_sytem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.employee_sytem.service.LeaveRequestServiceInterface;
import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing employee operations.
 * <p>
 * This controller handles requests for creating, retrieving, updating, and deleting
 * employee records via RESTful API endpoints.
 * </p>
 *

 */

/**
 * REST controller for managing employee-related operations.
 * <p>
 * This controller exposes endpoints for creating, retrieving, updating, and deleting employee records,
 * handling leave requests, and providing reports in a RESTful API style.
 * </p>
 * <p>
 * The controller includes endpoints for managing employee data, assigning employees to departments,
 * handling employee leave requests, and generating reports, including downloadable PDF reports.
 * </p>
 *
 * @author Kunal Kale
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestServiceInterface leaveRequestService;
    private final DepartmentService departmentService;
    private final DocumentService documentService;

    /**
     * Adds a new employee.
     *
     * @param employeeDto the employee data to be created
     * @return ResponseEntity containing the created EmployeeDto and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return ResponseEntity containing the EmployeeDto and HTTP status 200 (OK)
     */
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    /**
     * Retrieves all employees.
     *
     * @return ResponseEntity containing a list of EmployeeDto and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Updates an existing employee.
     *
     * @param employeeId      the ID of the employee to update
     * @param updatedEmployee the employee data to be updated
     * @return ResponseEntity containing the updated EmployeeDto and HTTP status 200 (OK)
     */
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId,
                                                      @RequestBody EmployeeDto updatedEmployee) {
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     * @return ResponseEntity containing a confirmation message and HTTP status 200 (OK)
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee Deleted Successfully:");
    }

    /**
     * Assign an employee to a department.
     *
     * @param employeeId the employee ID
     * @param departmentId the department ID to assign the employee to
     * @return ResponseEntity with HTTP status 200 (OK)
     */
    @PostMapping("/{employeeId}/assign")
    public ResponseEntity<Void> assignEmployeeToDepartment(@PathVariable Long employeeId,
                                                           @RequestParam Long departmentId) {
        employeeService.assignEmployeeToDepartment(employeeId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Fetch the department information for a specific employee by their ID.
     *
     * @param employeeId the ID of the employee
     * @return ResponseEntity containing the department information or HTTP status 404 (Not Found)
     */
    @GetMapping("/{employeeId}/department")
    public ResponseEntity<DepartmentDto> getEmployeeDepartment(@PathVariable Long employeeId) {
        DepartmentDto departmentDto = departmentService.getDepartmentForEmployee(employeeId);
        if (departmentDto != null) {
            return ResponseEntity.ok(departmentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Return 404 if no department is found for the employee
        }
    }

    /**
     * Search employees based on filters like name, department, and active status.
     *
     * @param name the name of the employee to search (optional)
     * @param departmentId the department ID to filter employees by (optional)
     * @param active the active status of employees to filter (optional)
     * @return ResponseEntity containing a list of matching EmployeeDto and HTTP status 200 (OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Boolean active) {
        List<EmployeeDto> employees = employeeService.searchEmployees(name, departmentId, active);
        return ResponseEntity.ok(employees);
    }

    /**
     * Create a leave request for an employee.
     *
     * @param employeeId the ID of the employee requesting leave
     * @param leaveRequestDTO the leave request data
     * @return ResponseEntity containing the created LeaveRequestDTO and HTTP status 201 (Created)
     */
    @PostMapping("/{employeeId}/leaves")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(
            @PathVariable Long employeeId, @RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDTO, employeeId);
        return new ResponseEntity<>(createdLeaveRequest, HttpStatus.CREATED);
    }

    /**
     * Approve a leave request.
     *
     * @param id the ID of the leave request to approve
     * @return ResponseEntity containing the approved LeaveRequestDTO and HTTP status 200 (OK)
     */
    @PutMapping("/leaves/{id}/approve")
    public ResponseEntity<LeaveRequestDTO> approveLeaveRequest(@PathVariable Long id) {
        LeaveRequestDTO approvedRequest = leaveRequestService.approveLeaveRequest(id);
        return ResponseEntity.ok(approvedRequest);
    }

    /**
     * Deny a leave request.
     *
     * @param id the ID of the leave request to deny
     * @return ResponseEntity containing the denied LeaveRequestDTO and HTTP status 200 (OK)
     */
    @PutMapping("/leaves/{id}/deny")
    public ResponseEntity<LeaveRequestDTO> denyLeaveRequest(@PathVariable Long id) {
        LeaveRequestDTO deniedRequest = leaveRequestService.denyLeaveRequest(id);
        return ResponseEntity.ok(deniedRequest);
    }

    /**
     * Retrieve all leave requests for a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return ResponseEntity containing a list of LeaveRequestDTO and HTTP status 200 (OK)
     */
    @GetMapping("/{employeeId}/leaves")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployee(@PathVariable Long employeeId) {
        List<LeaveRequestDTO> requests = leaveRequestService.getLeaveRequestsByEmployee(employeeId);
        return ResponseEntity.ok(requests);
    }

    /**
     * Retrieve all leave requests in the system.
     *
     * @return ResponseEntity containing a list of LeaveRequestDTO and HTTP status 200 (OK)
     */
    @GetMapping("/leaves")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        List<LeaveRequestDTO> requests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(requests);
    }

    /**
     * Retrieves the leave summary of an employee.
     *
     * @param employeeId the ID of the employee
     * @return ResponseEntity containing the LeaveSummaryDTO and HTTP status 200 (OK)
     */
    @GetMapping("/{employeeId}/leave-summary")
    public ResponseEntity<LeaveSummaryDTO> getLeaveSummary(@PathVariable Long employeeId) {
        LeaveSummaryDTO leaveSummary = leaveRequestService.getLeaveSummary(employeeId);
        return ResponseEntity.ok(leaveSummary);
    }

    /**
     * Endpoint to download the Employee Report as a PDF.
     * <p>
     * Accepts employee IDs as a query parameter and generates a PDF report.
     * </p>
     *
     * @param employeeIds a list of employee IDs for whom the report is generated
     * @return ResponseEntity containing the PDF report as a byte array and HTTP status 200 (OK)
     * @throws IOException if there's an error generating the PDF
     */
    @GetMapping("/employee-report")
    public ResponseEntity<byte[]> downloadEmployeeReport(@RequestParam List<Long> employeeIds) throws IOException {
        List<EmployeeDto> employees = employeeService.getEmployeesByIds(employeeIds);
        byte[] pdfContent = documentService.generateEmployeeReportPdf(employees);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employee_report.pdf");
        headers.add("Content-Type", "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

}
