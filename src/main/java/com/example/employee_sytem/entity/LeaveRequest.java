package com.example.employee_sytem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Entity class representing a leave request made by an employee in the system.
 * <p>
 * This class is mapped to the "leave_requests" table in the database and defines the attributes
 * associated with a leave request, including the employee who requested the leave,
 * the start and end dates, the reason for leave, and the current status of the request.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "leave_requests")
public class LeaveRequest {

    /**
     * The unique identifier for the leave request.
     * This field is automatically generated by the database.
     * <p>
     * This is the primary key for the leave request entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The employee associated with this leave request.
     * This field establishes a Many-to-One relationship with the Employee entity.
     * The foreign key is stored in the "employee_id" column in the leave_requests table.
     *
     * @see Employee
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * The start date of the leave request.
     * This field stores the date when the employee intends to begin their leave.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * The end date of the leave request.
     * This field stores the date when the employee intends to end their leave.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * The reason for the leave request.
     * This field stores the reason provided by the employee for the leave request.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private String reason;

    /**
     * The status of the leave request.
     * This field indicates whether the leave request is "pending", "approved", "denied", etc.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private String status;

    /**
     * Calculates the number of leave days requested by the employee.
     * This method calculates the number of days between the start and end date,
     * inclusive of both dates.
     *
     * @return the number of leave days taken by the employee
     */
    public long getLeaveDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both start and end date
    }





}