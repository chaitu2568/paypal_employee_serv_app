package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;

public interface EmployeeService {

    /**
     * Creates the {@link Employee} resource by employee.
     *
     * @param employee employee.
     * @return {@link Employee} resource.
     */
    public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException;

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    public Employee findEmployeeById(Integer id) throws EmployeeNotFoundException;
}
