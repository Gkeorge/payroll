package com.tarya.payroll.repository;

import com.tarya.payroll.BaseSetup;
import com.tarya.payroll.controller.dto.request.EmployeeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static com.tarya.payroll.TestUtil.DEFAULT_EMAIL;
import static com.tarya.payroll.TestUtil.getEmployee;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class EmployeeRepositoryTest extends BaseSetup {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(@Autowired EmployeeRepository employeeRepository) {
        EmployeeRequest employeeRequest = getEmployee();
        employeeRepository.save(createEmployee(employeeRequest));
    }

    @AfterEach
    void cleanUp() {
        this.employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Verify employee exists by email")
    void existsByEmailAddressIgnoreCase_ShouldExist() {
        boolean result = employeeRepository.existsByEmailAddressIgnoreCase(DEFAULT_EMAIL);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Verify employee does not exist by email")
    void existsByEmailAddressIgnoreCase_ShouldNotExist() {
        boolean result = employeeRepository.existsByEmailAddressIgnoreCase("Test");
        assertThat(result).isFalse();
    }
}