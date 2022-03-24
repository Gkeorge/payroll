package com.tarya.payroll.controller.dto.response;

import com.tarya.payroll.model.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeProjection {

    private String id;

    private String name;

    private String emailAddress;

    private String role;

    public EmployeeProjection(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.emailAddress = employee.getEmailAddress();
        this.role = employee.getRole();
    }

    public static EmployeeProjection toProjection(Employee employee) {
        return new EmployeeProjection(employee);
    }
}
