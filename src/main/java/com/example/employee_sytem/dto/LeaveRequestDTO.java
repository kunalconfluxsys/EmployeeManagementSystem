package com.example.employee_sytem.dto;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class LeaveRequestDTO {

    private Long id;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;  // PENDING , APPROVED , DENIED



}
