package com.example.employee_sytem.service.Impl;
import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.LeaveRequest;
import com.example.employee_sytem.exception.ResourceNotFoundException;
import com.example.employee_sytem.mapper.LeaveRequestMapper;
import com.example.employee_sytem.repository.EmployeeRepository;
import com.example.employee_sytem.repository.LeaveRequestRepository;
import com.example.employee_sytem.service.LeaveRequestServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the LeaveRequestServiceInterface to handle leave request management.
 * This service is responsible for creating, approving, denying, and retrieving leave requests,
 * as well as calculating and providing leave summaries for employees.
 */
@Service
@AllArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestServiceInterface {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository; // Repository for accessing employee data
    private final LeaveRequestMapper leaveRequestMapper; // Mapper for converting between DTOs and entities
    private static final int TOTAL_LEAVES_IN_A_YEAR = 27; // Total number of leaves allowed in a year

    /**
     * Creates a new leave request for an employee.
     *
     * @param leaveRequestDTO the DTO object containing leave request details.
     * @param employeeId the ID of the employee for whom the leave request is being created.
     * @return the created LeaveRequestDTO object representing the leave request.
     * @throws ResourceNotFoundException if the employee with the given ID does not exist.
     */
    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestDTO, employee);
        LeaveRequest createdRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toDTO(createdRequest);
    }

    /**
     * Approves a leave request for a given leave request ID.
     * If the employee has sufficient remaining leave balance, the leave request is approved.
     * Otherwise, an exception is thrown.
     *
     * @param id the ID of the leave request to approve.
     * @return the approved LeaveRequestDTO object.
     * @throws ResourceNotFoundException if the leave request with the given ID does not exist.
     * @throws RuntimeException if the employee does not have sufficient leave balance.
     */
    @Override
    public LeaveRequestDTO approveLeaveRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        request.setStatus("APPROVED");

        // Reduce the remaining leave balance if the leave is approved
        Employee employee = request.getEmployee();
        long totalLeaveDaysTaken = calculateTotalLeaveDaysTaken(employee);  // Change totalLeaveDaysTaken to long

        // Calculate the remaining leave days
        long remainingLeaveDays = TOTAL_LEAVES_IN_A_YEAR - totalLeaveDaysTaken;

        // If remaining leave days are > 0, approve the leave
        if (remainingLeaveDays > 0) {
            leaveRequestRepository.save(request);
        } else {
            // If no leaves remain, throw an exception
            throw new RuntimeException("Insufficient leave balance");
        }

        return leaveRequestMapper.toDTO(request);
    }

    /**
     * Denies a leave request for a given leave request ID.
     * Denied requests will not affect the leave balance.
     *
     * @param id the ID of the leave request to deny.
     * @return the denied LeaveRequestDTO object.
     * @throws ResourceNotFoundException if the leave request with the given ID does not exist.
     */
    @Override
    public LeaveRequestDTO denyLeaveRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        request.setStatus("DENIED");
        leaveRequestRepository.save(request);

        return leaveRequestMapper.toDTO(request);
    }

    /**
     * Retrieves all leave requests for a specific employee.
     *
     * @param employeeId the ID of the employee whose leave requests are to be retrieved.
     * @return a list of LeaveRequestDTO objects representing all the leave requests of the employee.
     * @throws ResourceNotFoundException if the employee with the given ID does not exist.
     */
    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<LeaveRequest> requests = leaveRequestRepository.findByEmployee(employee);
        return requests.stream().map(leaveRequestMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves all leave requests in the system.
     *
     * @return a list of all LeaveRequestDTO objects in the system.
     */
    @Override
    public List<LeaveRequestDTO> getAllLeaveRequests() {
        List<LeaveRequest> requests = leaveRequestRepository.findAll();
        return requests.stream().map(leaveRequestMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a leave summary for an employee, including the total number of leave days taken
     * and the remaining leave days for the current year.
     *
     * @param employeeId the ID of the employee whose leave summary is to be retrieved.
     * @return a LeaveSummaryDTO object containing the employee's leave summary.
     * @throws ResourceNotFoundException if the employee with the given ID does not exist.
     */
    @Override
    public LeaveSummaryDTO getLeaveSummary(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        long totalLeaveDaysTaken = calculateTotalLeaveDaysTaken(employee);  // Change to long
        long remainingLeaveDays = TOTAL_LEAVES_IN_A_YEAR - totalLeaveDaysTaken;

        return leaveRequestMapper.toLeaveSummaryDTO(employee, (int) totalLeaveDaysTaken, (int) remainingLeaveDays);  // Cast to int for the DTO
    }

    /**
     * Calculates the total number of leave days taken by an employee.
     * This includes all approved leave requests and calculates the days based on the
     * start and end dates of each leave request.
     *
     * @param employee the Employee entity for whom the leave days are to be calculated.
     * @return the total number of leave days taken by the employee.
     */
    private long calculateTotalLeaveDaysTaken(Employee employee) {
        List<LeaveRequest> approvedRequests = leaveRequestRepository.findByEmployeeAndStatus(employee, "APPROVED");

        long totalLeaveDaysTaken = 0;  // Initialize once, no re-assignment inside the loop

        // Iterate through each approved leave request and calculate the number of days
        for (LeaveRequest request : approvedRequests) {
            LocalDate startDate = request.getStartDate();
            LocalDate endDate = request.getEndDate();

            // Calculate the number of days between startDate and endDate (inclusive)
            long leaveDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

            // Accumulate the leave days
            totalLeaveDaysTaken += leaveDays;
        }

        return totalLeaveDaysTaken;  // Return the final total (as long)
    }


}



