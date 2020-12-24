package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeRequestValidationException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @RequestMapping("/v1/bfs/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") String id) throws EmployeeNotFoundException;

    /**
     * Creates the {@link Employee} resource by employee.
     *
     * @param employee employee.
     * @return {@link Employee} resource.
     */
    @PostMapping("/v1/bfs/employees")
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee)
        throws EmployeeAlreadyExistsException, EmployeeRequestValidationException;
}
