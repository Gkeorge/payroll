package com.tarya.payroll.model.entity;

import com.tarya.payroll.controller.dto.request.RegisterEmployee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tarya.payroll.TestUtil.*;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmployeeTest {

    @Test
    @DisplayName("Create employee should create an employee")
    void createEmployee_WithValidDetails_ShouldCreateEmployee() {
        RegisterEmployee employee = getEmployee();
        Employee newEmployee = createEmployee(employee);
        assertAll("Assertions",
                () -> assertThat(newEmployee).isNotNull(),
                () -> assertThat(newEmployee.getName()).isEqualTo(DEFAULT_NAME),
                () -> assertThat(newEmployee.getEmailAddress()).isEqualTo(DEFAULT_EMAIL),
                () -> assertThat(newEmployee.getRole()).isEqualTo(DEFAULT_ROLE));
    }

    @Test
    @DisplayName("Create employee with ID should create an employee")
    void createEmployeeWithId() {
        RegisterEmployee employee = getEmployee();
        String randomId = UUID.randomUUID().toString();
        Employee newEmployee = Employee.createEmployeeWithId(randomId, employee);
        assertAll("Assertions",
                () -> assertThat(newEmployee).isNotNull(),
                () -> assertThat(newEmployee.getId()).isEqualTo(randomId));
    }
}