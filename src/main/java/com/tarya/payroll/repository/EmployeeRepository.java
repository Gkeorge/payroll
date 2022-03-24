package com.tarya.payroll.repository;

import com.tarya.payroll.model.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    boolean existsByEmailAddressIgnoreCase(String emailAddress);
}
