package com.example.employee_sytem.repository;


import com.example.employee_sytem.dto.LeaveRequestDTO;
import com.example.employee_sytem.entity.Employee;
import com.example.employee_sytem.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    List<LeaveRequest> findByEmployee(Employee employee);

    List<LeaveRequestDTO> findByManagerId(Long managerId);

    List<LeaveRequest> findByEmployeeIn(List<Employee> employees);
}
