package com.example.employee_sytem.mapper;

import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.entity.LeaveRequest;
import org.springframework.stereotype.Component;
import com.example.employee_sytem.entity.Employee;
@Component
public class LeaveRequestMapper {
    public LeaveRequestDTO toDTO(LeaveRequest leaveRequest){

        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        return dto;
    }

    public LeaveRequest toEntity(LeaveRequestDTO dto, Employee employee) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus("PENDING"); // Default status
        return leaveRequest;
    }

}
