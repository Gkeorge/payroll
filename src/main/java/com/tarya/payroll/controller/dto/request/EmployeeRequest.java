package com.tarya.payroll.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9-_. ]{3,100})$")
    private String name;

    @Email
    @NotBlank
    private String emailAddress;

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9-_ ]{3,50})$")
    private String role;
}
