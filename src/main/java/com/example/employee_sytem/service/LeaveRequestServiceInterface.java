package com.example.employee_sytem.service;
import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import java.util.List;

/**
 * Service interface for managing leave requests in the employee system.
 * <p>
 * This interface defines methods for creating, approving, denying, and retrieving leave requests,
 * as well as providing leave summaries for employees.
 * </p>
 */
public interface LeaveRequestServiceInterface {

    /**
     * Creates a new leave request for an employee.
     * <p>
     * This method allows an employee to submit a leave request. The request will be
     * associated with the given employee's ID.
     * </p>
     *
     * @param leaveRequestDTO the details of the leave request to be created
     * @param employeeId the ID of the employee submitting the leave request
     * @return the created LeaveRequestDTO, including the generated ID
     */
    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO, Long employeeId);

    /**
     * Approves a leave request.
     * <p>
     * This method will change the status of a leave request to "APPROVED".
     * </p>
     *
     * @param id the ID of the leave request to be approved
     * @return the updated LeaveRequestDTO with status set to "APPROVED"
     */
    LeaveRequestDTO approveLeaveRequest(Long id);

    /**
     * Denies a leave request.
     * <p>
     * This method will change the status of a leave request to "DENIED".
     * </p>
     *
     * @param id the ID of the leave request to be denied
     * @return the updated LeaveRequestDTO with status set to "DENIED"
     */
    LeaveRequestDTO denyLeaveRequest(Long id);

    /**
     * Retrieves all leave requests for a specific employee.
     * <p>
     * This method allows fetching all the leave requests associated with a particular employee.
     * </p>
     *
     * @param employeeId the ID of the employee whose leave requests are to be fetched
     * @return a list of LeaveRequestDTOs for the specified employee
     */
    List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId);

    /**
     * Retrieves all leave requests in the system.
     * <p>
     * This method allows fetching all leave requests, regardless of the employee.
     * </p>
     *
     * @return a list of all LeaveRequestDTOs in the system
     */
    List<LeaveRequestDTO> getAllLeaveRequests();

    /**
     * Retrieves the leave summary for an employee.
     * <p>
     * This method calculates the total leave days taken, remaining leaves, and other relevant leave data
     * for an employee based on their leave requests.
     * </p>
     *
     * @param employeeId the ID of the employee whose leave summary is to be retrieved
     * @return the LeaveSummaryDTO containing the employee's leave details
     */
    LeaveSummaryDTO getLeaveSummary(Long employeeId);  // Get leave summary (remaining leaves)

}