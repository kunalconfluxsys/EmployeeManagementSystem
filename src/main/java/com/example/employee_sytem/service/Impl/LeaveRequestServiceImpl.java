package com.example.employee_sytem.service.Impl;

import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.LeaveRequest;
import com.example.employee_sytem.exception.ResourceNotFoundException;
import com.example.employee_sytem.mapper.LeaveRequestMapper;
import com.example.employee_sytem.repository.EmployeeRepository;
import com.example.employee_sytem.repository.LeaveRequestRepository;
import com.example.employee_sytem.service.LeaveRequestServiceInterface;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestServiceInterface {

    private final  LeaveRequestRepository leaveRequestRepository;
    private final  EmployeeRepository employeeRepository; // Assuming you have this repository
    private  final LeaveRequestMapper leaveRequestMapper;




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
        return leaveRequestMapper.toDTO(leaveRequestRepository.save(request));
    }

    @Override
    public LeaveRequestDTO denyLeaveRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        request.setStatus("DENIED");
        return leaveRequestMapper.toDTO(leaveRequestRepository.save(request));
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
    public List<LeaveRequestDTO> getLeavesForManager(Long managerId) {

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        // Get all employees managed by the manager
        List<Employee> employees = employeeRepository.findByManager(manager);

        // Get all leave requests for these employees
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeIn(employees);

        // Map leave requests to DTOs
        return leaveRequests.stream()
                .map(leaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }



}
