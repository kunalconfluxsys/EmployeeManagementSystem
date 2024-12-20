
package com.example.employee_sytem.mapper;

import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.dto.LeaveSummaryDTO;
import com.example.employee_sytem.entity.LeaveRequest;
import org.springframework.stereotype.Component;
import com.example.employee_sytem.entity.Employee;
/**
 * Mapper class for converting between LeaveRequest entities, LeaveRequestDTOs, and LeaveSummaryDTOs.
 * <p>
 * This class provides utility methods to map between LeaveRequest entities and their corresponding
 * Data Transfer Objects (LeaveRequestDTO) as well as a method to return a summary of the employee's leave status
 * (LeaveSummaryDTO).
 * </p>
 */
@Component
public class LeaveRequestMapper {

    /**
     * Converts a LeaveRequest entity to a LeaveRequestDTO.
     * <p>
     * This method takes a LeaveRequest entity object and converts it into a LeaveRequestDTO,
     * which is typically used to transfer data between the backend and the frontend.
     * </p>
     *
     * @param leaveRequest the LeaveRequest entity to be converted
     * @return the corresponding LeaveRequestDTO
     */
    public LeaveRequestDTO toDTO(LeaveRequest leaveRequest) {
        // Creating the DTO and mapping properties
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        return dto;
    }

    /**
     * Converts a LeaveRequestDTO to a LeaveRequest entity.
     * <p>
     * This method converts a LeaveRequestDTO, typically received from an API request,
     * into a LeaveRequest entity that is suitable for database operations.
     * </p>
     *
     * @param dto the LeaveRequestDTO to be converted
     * @param employee the Employee associated with the leave request
     * @return the corresponding LeaveRequest entity
     */
    public LeaveRequest toEntity(LeaveRequestDTO dto, Employee employee) {
        // Creating a new LeaveRequest entity and setting its properties
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);  // Setting the employee for the leave request
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus("PENDING"); // Default status set to "PENDING"
        return leaveRequest;
    }

    /**
     * Converts the employee's leave data into a LeaveSummaryDTO.
     * <p>
     * This method takes the employee's leave data and returns a summary, including
     * the total number of leave days, the leave days taken, and the remaining leave days.
     * </p>
     *
     * @param employee the employee whose leave summary is to be generated
     * @param totalLeaveDaysTaken the number of leave days the employee has taken
     * @param remainingLeaveDays the remaining leave days the employee has
     * @return a LeaveSummaryDTO containing the employee's leave summary
     */
    public LeaveSummaryDTO toLeaveSummaryDTO(Employee employee, int totalLeaveDaysTaken, int remainingLeaveDays) {
        // Assuming total leave days in a year is 27
        int totalLeaveDays = 27;

        // Generating the employee's full name
        String fullName = employee.getFirstName() + " " + employee.getLastName();

        // Returning the leave summary DTO
        return new LeaveSummaryDTO(
                employee.getId(),
                fullName,  // Full name generated by concatenating first and last name
                totalLeaveDays, // Total leave days allowed per year
                totalLeaveDaysTaken, // Number of leave days taken
                remainingLeaveDays // Number of leave days remaining
        );
    }

}
