package com.tarya.payroll.controller.dto.request;

import lombok.Getter;

@Getter
public class RegisterEmployee extends EmployeeRequest {

    public RegisterEmployee(String name, String email, String role) {
        super(name, email, role);
    }
}
