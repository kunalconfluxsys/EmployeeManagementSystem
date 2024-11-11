package com.example.employee_sytem.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for Leave Summary.
 * <p>
 * This class is used to transfer the leave summary information for an employee
 * between the layers of the application. It encapsulates data such as total leave days,
 * taken leaves, and remaining leaves.
 * </p>
 */
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LeaveSummaryDTO {

    /**
     * The unique identifier for the employee.
     * <p>
     * This field represents the ID of the employee whose leave summary is being provided.
     * It is used to associate the leave summary with a specific employee.
     * </p>
     */
    private Long employeeId;

    /**
     * The full name of the employee.
     * <p>
     * This field contains the employee's full name and is used for presenting
     * the leave summary with more context to the user.
     * </p>
     */
    private String employeeName;

    /**
     * The total number of leave days allowed for the employee in the year.
     * <p>
     * This field represents the total leave days that the employee is entitled to
     * take for the year. For example, an employee might be allowed 27 days of leave
     * per year, depending on company policy.
     * </p>
     */
    private int totalLeaveDays;

    /**
     * The number of leave days that the employee has already taken.
     * <p>
     * This field stores the total number of leave days the employee has used
     * during the year. It is used to track how many days the employee has taken off.
     * </p>
     */
    private int takenLeaves;

    /**
     * The number of remaining leave days available for the employee.
     * <p>
     * This field calculates how many leave days the employee has left to take
     * during the year, based on the total leave days minus the taken leaves.
     * </p>
     */
    private int remainingLeaves;
}


