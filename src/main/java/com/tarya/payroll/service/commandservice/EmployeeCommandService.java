package com.tarya.payroll.service.commandservice;

import com.tarya.payroll.controller.dto.request.RegisterEmployee;
import com.tarya.payroll.controller.dto.request.UpdateRegisteredEmployee;
import com.tarya.payroll.controller.dto.response.EmployeeProjection;
import com.tarya.payroll.exception.EmployeeEmailAlreadyExistsException;
import com.tarya.payroll.exception.EmployeeNotFoundException;
import com.tarya.payroll.model.entity.Employee;
import com.tarya.payroll.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tarya.payroll.controller.dto.response.EmployeeProjection.toProjection;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static com.tarya.payroll.model.entity.Employee.createEmployeeWithId;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService {

    private final EmployeeRepository employeeRepository;

    public String registerEmployee(RegisterEmployee registerEmployeeRequest) {
        verifyEmailIsUniqueOrThrowException(registerEmployeeRequest.getEmailAddress());

        Employee newEmployee = createEmployee(registerEmployeeRequest);
        Employee employee = employeeRepository.save(newEmployee);
        return employee.getId();
    }

    private void verifyEmailIsUniqueOrThrowException(String emailAddress) {
        if (employeeRepository.existsByEmailAddressIgnoreCase(emailAddress))
            throw new EmployeeEmailAlreadyExistsException("Email already exists " + emailAddress);
    }

    public EmployeeProjection updatedRegisteredEmployee(String employeeId, UpdateRegisteredEmployee updateRegisteredEmployeeRequest) {
        verifyEmailIsUniqueOrThrowException(updateRegisteredEmployeeRequest.getEmailAddress());

        Employee updatedEmployee = employeeRepository.findById(employeeId)
                .map(emp -> createEmployeeWithId(employeeId, updateRegisteredEmployeeRequest))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found for ID: " + employeeId));
        employeeRepository.save(updatedEmployee);
        return toProjection(updatedEmployee);
    }

    public void deleteRegisteredEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
