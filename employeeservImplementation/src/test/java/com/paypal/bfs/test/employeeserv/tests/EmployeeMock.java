package com.paypal.bfs.test.employeeserv.tests;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.util.EntityMapper;

public class EmployeeMock {
    protected com.paypal.bfs.test.employeeserv.model.Employee mockEmployeeEntity() {
        Employee employee = mockEmployee();
        EntityMapper entityMapper = new EntityMapper();
        com.paypal.bfs.test.employeeserv.model.Employee employeeEntity = entityMapper.toDbEntity(employee);
        return employeeEntity;
    }

    protected Employee mockEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Jon");
        employee.setLastName("Doe");
        employee.setDateOfBirth("2020-12-23");
        Address address = new Address();
        address.setCity("abc");
        address.setCountry("USA");
        address.setLine1("line 1");
        address.setLine2("line 2 address");
        address.setState("CA");
        address.setZipCode(32423423);
        employee.setAddress(address);
        return employee;
    }

    protected Employee mockInvalidEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("");
        employee.setLastName("");
        employee.setDateOfBirth("2020-31-23");
        Address address = new Address();
        address.setCity("");
        address.setCountry("");
        address.setLine1("");
        address.setLine2("");
        address.setState("");
        employee.setAddress(address);
        return employee;
    }
}
