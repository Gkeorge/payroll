package com.tarya.payroll.config.db.changelogs;

import com.tarya.payroll.controller.dto.request.EmployeeRequest;
import com.tarya.payroll.model.entity.Employee;
import com.tarya.payroll.repository.EmployeeRepository;
import io.mongock.api.annotations.*;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "employee-initializer", order = "1", author = "George")
public class EmployeeInitializerChangeLog {

    public static final String EMPLOYEES = "employees";

    @BeforeExecution
    public void beforeExecution(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.collectionExists(EMPLOYEES))
            mongoTemplate.createCollection(EMPLOYEES);
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(EMPLOYEES);
    }

    @Execution
    public void execution(EmployeeRepository employeeRepository) {
        EmployeeRequest employee = getEmployee();
        if (!employeeRepository.existsByEmailAddressIgnoreCase(employee.getEmailAddress()))
            employeeRepository.save(Employee.createEmployee(employee));
    }

    @RollbackExecution
    public void rollbackExecution(EmployeeRepository employeeRepository) {
        employeeRepository.deleteAll();
    }

    private static EmployeeRequest getEmployee() {
        return new EmployeeRequest("George", "gorkofi@gmail.com", "Admin");
    }
}
