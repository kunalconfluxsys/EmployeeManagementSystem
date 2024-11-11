package com.example.employee_sytem.dto;
import lombok.*;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Leave Request.
 * <p>
 * This class is used to transfer leave request data between the layers of the application.
 * It encapsulates the details of a leave request, including the employee ID, start and end dates,
 * reason for the leave, and the status of the request (e.g., PENDING, APPROVED, DENIED).
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {

    /**
     * The unique identifier for the leave request.
     * <p>
     * This ID uniquely identifies the leave request in the system.
     * It is used to reference the leave request when updating or deleting it.
     * </p>
     */
    private Long id;

    /**
     * The ID of the employee making the leave request.
     * <p>
     * This field is used to associate the leave request with a specific employee.
     * It represents the employee who is requesting leave.
     * </p>
     */
    private Long employeeId;

    /**
     * The start date of the leave.
     * <p>
     * This field indicates the first day of the leave period.
     * The start date must be provided by the employee when requesting leave.
     * </p>
     */
    private LocalDate startDate;

    /**
     * The end date of the leave.
     * <p>
     * This field indicates the last day of the leave period.
     * The end date must be provided by the employee when requesting leave.
     * </p>
     */
    private LocalDate endDate;

    /**
     * The reason for the leave request.
     * <p>
     * This field stores the employee's reason for requesting leave (e.g., personal, sick, vacation).
     * It provides context for the leave request and is usually submitted by the employee.
     * </p>
     */
    private String reason;

    /**
     * The current status of the leave request.
     * <p>
     * This field tracks the status of the leave request, which can be one of the following:
     * - PENDING: The leave request is still awaiting approval.
     * - APPROVED: The leave request has been approved by the manager or HR.
     * - DENIED: The leave request has been rejected.
     * </p>
     */
    private String status;  // Possible values: PENDING, APPROVED, DENIED
}




