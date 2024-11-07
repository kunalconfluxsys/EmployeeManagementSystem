package com.example.employee_sytem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class LeaveSummaryDTO {

    private Long employeeId;
    private String employeeName;
    private int totalLeaveDays;  // Total leave days allowed in the year (27 for example)
    private int takenLeaves;// Leaves taken

    private int remainingLeaves; //Remaining Leaves


}
