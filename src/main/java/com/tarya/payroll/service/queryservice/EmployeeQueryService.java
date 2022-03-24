package com.tarya.payroll.service.queryservice;

import com.tarya.payroll.controller.dto.response.EmployeeProjection;
import com.tarya.payroll.exception.EmployeeNotFoundException;
import com.tarya.payroll.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeQueryService {

    private final EmployeeRepository employeeRepository;

    public Page<EmployeeProjection> getRegisteredEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(EmployeeProjection::toProjection);
    }

    public EmployeeProjection getRegisteredEmployee(String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(EmployeeProjection::toProjection)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found for ID: " + employeeId));
    }

}
