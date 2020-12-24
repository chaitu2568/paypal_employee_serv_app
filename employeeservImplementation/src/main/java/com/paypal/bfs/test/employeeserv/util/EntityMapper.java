package com.paypal.bfs.test.employeeserv.util;

import org.springframework.stereotype.Component;

import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;

@Component
public class EntityMapper {

    public Employee toDbEntity(com.paypal.bfs.test.employeeserv.api.model.Employee employee){
        Employee employeeEntity = new Employee();
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setDob(employee.getDateOfBirth());
        Address addressEntity = new Address();
        addressEntity.setLine1(employee.getAddress().getLine1());
        addressEntity.setLine2(employee.getAddress().getLine2());
        addressEntity.setCity(employee.getAddress().getCity());
        addressEntity.setState(employee.getAddress().getState());
        addressEntity.setCountry(employee.getAddress().getCountry());
        addressEntity.setZipCode(employee.getAddress().getZipCode());
        employeeEntity.setAddress(addressEntity);
        return employeeEntity;
    }

    public com.paypal.bfs.test.employeeserv.api.model.Employee fromDbEntity(Employee employeeEntity){
        com.paypal.bfs.test.employeeserv.api.model.Employee employee = new com.paypal.bfs.test.employeeserv.api.model.Employee();
        employee.setId(employeeEntity.getId());
        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setDateOfBirth(employeeEntity.getDob());
        com.paypal.bfs.test.employeeserv.api.model.Address address = new com.paypal.bfs.test.employeeserv.api.model.Address();
        address.setLine1(employeeEntity.getAddress().getLine1());
        address.setLine2(employeeEntity.getAddress().getLine2());
        address.setCity(employeeEntity.getAddress().getCity());
        address.setState(employeeEntity.getAddress().getState());
        address.setCountry(employeeEntity.getAddress().getCountry());
        address.setZipCode(employeeEntity.getAddress().getZipCode());
        employee.setAddress(address);
        return employee;

    }

}
