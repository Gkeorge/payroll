package com.tarya.payroll;

import com.tarya.payroll.controller.dto.request.RegisterEmployee;
import com.tarya.payroll.controller.dto.request.UpdateRegisteredEmployee;

public abstract class TestUtil {

    public static final String DEFAULT_NAME = "George";
    public static final String DEFAULT_EMAIL = "gorkofi@gmail.com";
    public static final String DEFAULT_ROLE = "Amin";
    public static final String TARYA = "Tarya";
    public static final String TARYA_EMAIL = "tarya@gmail.com";
    public static final String TARYA_ROLE = "Admin";

    public static RegisterEmployee getEmployee() {
        return new RegisterEmployee(DEFAULT_NAME, DEFAULT_EMAIL, DEFAULT_ROLE);
    }

    public static RegisterEmployee getEmployee(String name, String emailAddress, String role) {
        return new RegisterEmployee(name, emailAddress, role);
    }

    public static UpdateRegisteredEmployee getUpdatedEmployeeDetails() {
        return new UpdateRegisteredEmployee(TARYA, TARYA_EMAIL, TARYA_ROLE);
    }

}
