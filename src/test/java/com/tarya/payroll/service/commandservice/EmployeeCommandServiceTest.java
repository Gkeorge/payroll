package com.tarya.payroll.service.commandservice;

import com.tarya.payroll.BaseSetup;
import com.tarya.payroll.controller.dto.response.EmployeeProjection;
import com.tarya.payroll.model.entity.Employee;
import com.tarya.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static com.tarya.payroll.TestUtil.*;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class EmployeeCommandServiceTest extends BaseSetup {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeCommandService employeeCommandService;

    @BeforeEach
    void setUp() {
        employeeCommandService = new EmployeeCommandService(employeeRepository);
    }

    @AfterEach
    void cleanUp() {
        this.employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Register employee should save an employee")
    void registerEmployee_WithValidDetails_ShouldSaveEmployee() {
        String employeeId = employeeCommandService.registerEmployee(getEmployee());
        assertAll("Assertions",
                () -> assertThat(employeeId).isNotNull(),
                () -> assertThat(employeeId).isNotBlank());
    }

    @Test
    @DisplayName("Update registered employee should save an employee")
    void updatedRegisteredEmployee_WithValid_ShouldUpdateEmployeeDetails() {
        Employee employee = employeeRepository.save(createEmployee(getEmployee()));
        EmployeeProjection updatedEmployee = employeeCommandService.updatedRegisteredEmployee(employee.getId()
                , getUpdatedEmployeeDetails());
        assertAll("Assertions",
                () -> assertThat(updatedEmployee.getId()).isNotNull(),
                () -> assertThat(updatedEmployee.getId()).isNotBlank(),
                () -> assertThat(updatedEmployee.getName()).isEqualTo(TARYA),
                () -> assertThat(updatedEmployee.getEmailAddress()).isEqualTo(TARYA_EMAIL),
                () -> assertThat(updatedEmployee.getRole()).isEqualTo(TARYA_ROLE));
    }

    @Test
    @DisplayName("Delete registered employee should delete the employee")
    void deleteRegisteredEmployee() {
        Employee employee = employeeRepository.save(createEmployee(getEmployee()));
        employeeCommandService.deleteRegisteredEmployee(employee.getId());
    }

}