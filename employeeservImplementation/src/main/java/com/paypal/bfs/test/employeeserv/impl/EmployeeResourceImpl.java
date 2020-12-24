package com.paypal.bfs.test.employeeserv.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeRequestValidationException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.util.RequestValidator;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeResourceImpl.class);

    private EmployeeService employeeService;

    @Autowired
    public EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) throws EmployeeNotFoundException {
        Employee employee = employeeService.findEmployeeById(Integer.valueOf(id));
        logger.info("Found Employee with id: {}", employee.getId());
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee)
        throws EmployeeAlreadyExistsException, EmployeeRequestValidationException {
        RequestValidator.checkEmployeeRequestValidations(employee);
        Employee employeeResponse = employeeService.saveEmployee(employee);
        logger.info("Created Employee with id: {}", employeeResponse.getId());
        return new ResponseEntity<Employee>(employeeResponse, HttpStatus.CREATED);
    }
}
