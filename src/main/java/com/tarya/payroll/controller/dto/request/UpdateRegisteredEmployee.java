package com.tarya.payroll.controller.dto.request;

import lombok.Getter;

@Getter
public class UpdateRegisteredEmployee extends EmployeeRequest {

    public UpdateRegisteredEmployee(String name, String emailAddress, String role) {
        super(name, emailAddress, role);
    }
}
