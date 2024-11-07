
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

@Service
@AllArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestServiceInterface {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository; // Assuming you have this repository
    private final LeaveRequestMapper leaveRequestMapper;
    private static final int TOTAL_LEAVES_IN_A_YEAR = 27; // Total number of leaves allowed in a year


    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestDTO, employee);
        LeaveRequest createdRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toDTO(createdRequest);
    }

    @Override
    public LeaveRequestDTO approveLeaveRequest(Long id) {

        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        request.setStatus("APPROVED");

        // Reduce the remaining leave balance if the leave is approved
        Employee employee = request.getEmployee();
        long totalLeaveDaysTaken = calculateTotalLeaveDaysTaken(employee);  // Change totalLeaveDaysTaken to long

        // Calculate the remaining leave days
        long remainingLeaveDays = TOTAL_LEAVES_IN_A_YEAR - totalLeaveDaysTaken;  // Use long for remainingLeaveDays

        // If remaining leave days are > 0, approve the leave
        if (remainingLeaveDays > 0) {
            leaveRequestRepository.save(request);
        } else {
            // If no leaves remain, throw an exception or handle the logic accordingly
            throw new RuntimeException("Insufficient leave balance");
        }

        return leaveRequestMapper.toDTO(request);


    }

    @Override
    public LeaveRequestDTO denyLeaveRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        request.setStatus("DENIED");
        // Denied leave request should not affect the leave balance
        leaveRequestRepository.save(request);

        return leaveRequestMapper.toDTO(request);
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<LeaveRequest> requests = leaveRequestRepository.findByEmployee(employee);
        return requests.stream().map(leaveRequestMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getAllLeaveRequests() {
        List<LeaveRequest> requests = leaveRequestRepository.findAll();
        return requests.stream().map(leaveRequestMapper::toDTO).collect(Collectors.toList());
    }



    @Override
    // Method to calculate remaining leaves and return leave summary
    public LeaveSummaryDTO getLeaveSummary(Long employeeId) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Get the total leave days taken by the employee (approved leaves)
        long totalLeaveDaysTaken = calculateTotalLeaveDaysTaken(employee);  // Change to long

        // Calculate the remaining leave days
        long remainingLeaveDays = TOTAL_LEAVES_IN_A_YEAR - totalLeaveDaysTaken;  // Use long for remainingLeaveDays

        // Map the data to LeaveSummaryDTO
        return leaveRequestMapper.toLeaveSummaryDTO(employee, (int) totalLeaveDaysTaken, (int) remainingLeaveDays);  // Cast to int for the DTO
    }

    /**
     * Method to calculate the total number of leave days taken by the employee.
     * This includes all approved leave requests, and the days are calculated based on the
     * start and end dates of the leave request.
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



