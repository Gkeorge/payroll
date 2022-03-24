package com.tarya.payroll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarya.payroll.controller.dto.request.EmployeeRequest;
import com.tarya.payroll.controller.dto.request.RegisterEmployee;
import com.tarya.payroll.controller.dto.request.UpdateRegisteredEmployee;
import com.tarya.payroll.controller.dto.response.EmployeeProjection;
import com.tarya.payroll.model.entity.Employee;
import com.tarya.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static com.tarya.payroll.TestUtil.*;
import static com.tarya.payroll.model.entity.Employee.createEmployee;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PayrollApplicationIntegrationTests extends BaseSetup {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static String employeeId;

    @BeforeAll
    static void setUp(@Autowired EmployeeRepository employeeRepository) {
        EmployeeRequest employeeRequest = getEmployee();
        Employee employee = employeeRepository.save(createEmployee(employeeRequest));
        employeeId = employee.getId();
    }

    @Test
    @DisplayName("Get registered employees")
    void getRegisteredEmployees_ShouldReturnRegisteredEmployees() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = this.testRestTemplate.getForEntity("/api/v1/employees", String.class);

        TestPageImpl<EmployeeProjection> pageResource = objectMapper.readValue(responseEntity.getBody(),
                new TypeReference<>() {});

        assertAll("Assertions",
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
                () -> assertThat(pageResource.getNumberOfElements()).isNotZero());
    }

    @Test
    @DisplayName("Get registered employee")
    void getRegisteredEmployee_ShouldReturnRegisteredEmployee() {
        ResponseEntity<EmployeeProjection> responseEntity = this.testRestTemplate.getForEntity("/api/v1/employees/{employeeId}",
                EmployeeProjection.class, employeeId);
        EmployeeProjection employeeProjection = responseEntity.getBody();

        assertAll("Assertions",
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
                () -> assertThat(employeeProjection).isNotNull(),
                () -> assertThat(employeeProjection.getId()).isEqualTo(employeeProjection.getId()),
                () -> assertThat(employeeProjection.getName()).isEqualTo(employeeProjection.getName()),
                () -> assertThat(employeeProjection.getEmailAddress()).isEqualTo(employeeProjection.getEmailAddress()),
                () -> assertThat(employeeProjection.getRole()).isEqualTo(employeeProjection.getRole()));
    }

    @Test
    @DisplayName("Register employee")
    void registerEmployee_ShouldRegisterEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<RegisterEmployee> request = new HttpEntity<>(getEmployee("Tarya", "tarya@hotmail.com",
                "Admin"), headers);

        ResponseEntity<Void> responseEntity = this.testRestTemplate.postForEntity("/api/v1/employees",
                request, Void.class);

        assertAll("Assertions",
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED),
                () -> assertThat(requireNonNull(responseEntity.getHeaders().getLocation()).toString()).isNotBlank());
    }

    @Test
    @DisplayName("Update registered employee")
    void updatedRegisteredEmployee_ShouldUpdateEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<UpdateRegisteredEmployee> request = new HttpEntity<>(getUpdatedEmployeeDetails(), headers);

        ResponseEntity<EmployeeProjection> responseEntity = this.testRestTemplate.exchange("/api/v1/employees/{employeeId}", PUT,
                request, EmployeeProjection.class, employeeId);
        EmployeeProjection updatedEmployee = responseEntity.getBody();

        assertAll("Assertions",
                () -> assertThat(updatedEmployee.getId()).isNotBlank(),
                () -> assertThat(updatedEmployee.getName()).isEqualTo(TARYA),
                () -> assertThat(updatedEmployee.getEmailAddress()).isEqualTo(TARYA_EMAIL),
                () -> assertThat(updatedEmployee.getRole()).isEqualTo(TARYA_ROLE));

    }

    @Test
    @DisplayName("Delete registered employee")
    void deleteRegisteredEmployee() {
        this.testRestTemplate.delete("/api/v1/employees/{employeeId}", employeeId);
    }
}
