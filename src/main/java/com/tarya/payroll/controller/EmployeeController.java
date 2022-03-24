package com.tarya.payroll.controller;

import com.tarya.payroll.controller.dto.request.RegisterEmployee;
import com.tarya.payroll.controller.dto.request.UpdateRegisteredEmployee;
import com.tarya.payroll.controller.dto.response.EmployeeProjection;
import com.tarya.payroll.service.commandservice.EmployeeCommandService;
import com.tarya.payroll.service.queryservice.EmployeeQueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {


    private final EmployeeQueryService employeeQueryService;
    private final EmployeeCommandService employeeCommandService;

    /**
     * Get Registered Employees
     *
     * @param pageable
     * @return Page<EmployeeProjection>
     */
    @GetMapping
    @ApiOperation(value = "Get registered employees", response = ResponseEntity.class)
    public ResponseEntity<Page<EmployeeProjection>> getRegisteredEmployees(Pageable pageable) {
        return new ResponseEntity<>(employeeQueryService.getRegisteredEmployees(pageable), OK);
    }

    /**
     * Get Registered Employee
     *
     * @param employeeId
     * @return EmployeeProjection
     */
    @GetMapping("/{employeeId}")
    @ApiOperation(value = "Get registered employee", response = ResponseEntity.class)
    public ResponseEntity<EmployeeProjection> getRegisteredEmployee(@ApiParam(value = "Employee ID", required = true)
                                                                    @PathVariable String employeeId) {
        return new ResponseEntity<>(employeeQueryService.getRegisteredEmployee(employeeId), OK);
    }

    /**
     * Register Employee
     *
     * @param registerEmployeeRequest
     * @return Returns the id of the employee in the Location header response
     */
    @PostMapping
    @ApiOperation(value = "Register employee", response = ResponseEntity.class)
    public ResponseEntity<Void> registerEmployee(@Valid @RequestBody RegisterEmployee registerEmployeeRequest) {
        String employeeId = employeeCommandService.registerEmployee(registerEmployeeRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(EmployeeController.class).slash(employeeId).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update Registered Employee
     *
     * @param employeeId
     * @param updateRegisteredEmployeeRequest
     * @return EmployeeProjection
     */
    @PutMapping("/{employeeId}")
    @ApiOperation(value = "Update employee", response = ResponseEntity.class)
    public ResponseEntity<EmployeeProjection> updatedRegisteredBanner(@ApiParam(value = "Employee ID", required = true) @PathVariable String employeeId,
                                                                      @Valid @RequestBody UpdateRegisteredEmployee updateRegisteredEmployeeRequest) {
        return new ResponseEntity<>(employeeCommandService.updatedRegisteredEmployee(employeeId, updateRegisteredEmployeeRequest), OK);
    }

    /**
     * Delete Registered Employee
     *
     * @param employeeId
     * @return void response
     */
    @DeleteMapping("/{employeeId}")
    @ApiOperation(value = "Delete employee", response = ResponseEntity.class)
    public ResponseEntity<Void> deleteRegisteredEmployee(@ApiParam(value = "Employee ID", required = true) @PathVariable String employeeId) {
        employeeCommandService.deleteRegisteredEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
