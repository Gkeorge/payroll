package com.tarya.payroll.model.entity;

import com.tarya.payroll.controller.dto.request.EmployeeRequest;
import com.tarya.payroll.model.audit.AuditMetaData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "employees")
public class Employee extends AuditMetaData {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String role;

    @NonNull
    @Indexed(unique = true)
    private String emailAddress;

    private Employee(EmployeeRequest registerEmployeeRequest) {
        setData(registerEmployeeRequest);
    }

    private void setData(EmployeeRequest registerEmployeeRequest) {
        this.name = registerEmployeeRequest.getName();
        this.role = registerEmployeeRequest.getRole();
        this.emailAddress = registerEmployeeRequest.getEmailAddress();
    }

    private Employee(String employeeId, EmployeeRequest employeeRequest) {
        this.id = employeeId;
        setData(employeeRequest);
    }

    public static Employee createEmployee(EmployeeRequest employeeRequest) {
        return new Employee(employeeRequest);
    }

    public static Employee createEmployeeWithId(String employeeId, EmployeeRequest employeeRequest) {
        return new Employee(employeeId, employeeRequest);
    }
}
