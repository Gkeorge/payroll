package com.tarya.payroll.exception;

import lombok.NonNull;

public class EmployeeEmailAlreadyExistsException extends RuntimeException {

    public EmployeeEmailAlreadyExistsException(@NonNull String message) {
        super(message);
    }
}
