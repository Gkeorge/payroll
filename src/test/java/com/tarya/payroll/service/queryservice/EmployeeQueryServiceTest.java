package com.tarya.payroll.service.queryservice;

import com.tarya.payroll.BaseSetup;
import com.tarya.payroll.controller.dto.request.EmployeeRequest;
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
import org.springframework.data.domain.Page;

import static com.tarya.payroll.TestUtil.getEmployee;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.Pageable.ofSize;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class EmployeeQueryServiceTest extends BaseSetup {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeQueryService employeeQueryService;

    private String employeeId;

    @BeforeEach
    void setUp() {
        employeeQueryService = new EmployeeQueryService(employeeRepository);
        EmployeeRequest employeeRequest = getEmployee();
        Employee employee = employeeRepository.save(createEmployee(employeeRequest));
        employeeId = employee.getId();
    }

    @AfterEach
    void cleanUp() {
        this.employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Get registered employees")
    void getRegisteredEmployees() {
        Page<EmployeeProjection> registeredEmployees = employeeQueryService.getRegisteredEmployees(ofSize(5));
        assertThat(registeredEmployees.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Get registered employee")
    void getRegisteredEmployee() {
        EmployeeProjection registeredEmployee = employeeQueryService.getRegisteredEmployee(employeeId);
        assertAll("Assertions",
                () -> assertThat(registeredEmployee).isNotNull(),
                () -> assertThat(registeredEmployee.getId()).isEqualTo(registeredEmployee.getId()),
                () -> assertThat(registeredEmployee.getName()).isEqualTo(registeredEmployee.getName()),
                () -> assertThat(registeredEmployee.getEmailAddress()).isEqualTo(registeredEmployee.getEmailAddress()),
                () -> assertThat(registeredEmployee.getRole()).isEqualTo(registeredEmployee.getRole()));
    }
}