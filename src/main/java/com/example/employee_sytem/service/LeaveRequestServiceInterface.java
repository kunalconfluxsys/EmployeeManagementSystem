package com.example.employee_sytem.service;

import com.example.employee_sytem.dto.LeaveRequestDTO;

import java.util.List;

public interface LeaveRequestServiceInterface {

    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO, Long employeeId);
    LeaveRequestDTO approveLeaveRequest(Long id);
    LeaveRequestDTO denyLeaveRequest(Long id);
    List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId);
    List<LeaveRequestDTO> getAllLeaveRequests();
    List<LeaveRequestDTO> getLeavesForManager(Long managerId);
}
