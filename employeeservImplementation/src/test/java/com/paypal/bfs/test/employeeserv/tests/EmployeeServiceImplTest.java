package com.paypal.bfs.test.employeeserv.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeServiceImpl;
import com.paypal.bfs.test.employeeserv.util.EntityMapper;

public class EmployeeServiceImplTest extends EmployeeMock{

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = mockEmployee();

        when(employeeRepository.findByFirstNameAndLastNameAndDob(anyString(), anyString(),anyString())).thenReturn(null);

        com.paypal.bfs.test.employeeserv.model.Employee employeeEntity = mockEmployeeEntity();
        employeeEntity.setId(1);
        when(employeeRepository.save(any(com.paypal.bfs.test.employeeserv.model.Employee.class))).thenReturn(employeeEntity);

        Employee savedEmployee = new Employee();
        try {
            savedEmployee = employeeServiceImpl.saveEmployee(employee);
        } catch (EmployeeAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertEquals(employeeEntity.getId(), savedEmployee.getId());
        verify(employeeRepository, times(1)).save(any(com.paypal.bfs.test.employeeserv.model.Employee.class));
    }

    @Test
    public void testSaveEmployeeWithExistingEmployee() {

        Employee employee = mockEmployee();

        com.paypal.bfs.test.employeeserv.model.Employee employeeEntity = mockEmployeeEntity();
        employeeEntity.setId(1);
        when(employeeRepository.findByFirstNameAndLastNameAndDob(anyString(), anyString(),anyString())).thenReturn(employeeEntity);

        when(employeeRepository.save(any(com.paypal.bfs.test.employeeserv.model.Employee.class))).thenReturn(employeeEntity);

        Employee savedEmployee = new Employee();
        try {
            savedEmployee = employeeServiceImpl.saveEmployee(employee);
        } catch (EmployeeAlreadyExistsException e) {
            assertEquals("Employee already exists with the same details", e.getMessage());
        }
        verify(employeeRepository, times(0)).save(any(com.paypal.bfs.test.employeeserv.model.Employee.class));

    }

    @Test(expected = Exception.class)
    public void testSaveEmployeeWhenException() {

        Employee employee = mockEmployee();

        when(employeeRepository.findByFirstNameAndLastNameAndDob(anyString(), anyString(),anyString()))
            .thenThrow(new RuntimeException("invalid data"));

        try {
            employeeServiceImpl.saveEmployee(employee);
        } catch (EmployeeAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void testFindEmployeeByIdWhenException() {
        Integer empId = 1;

        when(employeeRepository.findById(Mockito.anyInt())).thenThrow(new RuntimeException("unknown error"));

        try {
            employeeServiceImpl.findEmployeeById(empId);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
        }
        verify(employeeRepository, times(1)).findById(Mockito.anyInt());
    }

    @Test
    public void testFindEmployeeByIdWithExistingId() {
        Integer empId = 1;

        com.paypal.bfs.test.employeeserv.model.Employee employeeEntity = mockEmployeeEntity();
        employeeEntity.setId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));

        // Then
        Employee employee = new Employee();
        try {
            employee = employeeServiceImpl.findEmployeeById(empId);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
        }
       assertEquals(employeeEntity.getId(),employee.getId());
        verify(employeeRepository, times(1)).findById(Mockito.anyInt());
    }

    @Test
    public void testFindEmployeeByIdWithNotExistingId() {
        Integer empId = 1;

        when(employeeRepository.findById(empId)).thenReturn(Optional.empty());

        Employee employee = null;
        try {
            employee = employeeServiceImpl.findEmployeeById(empId);
        } catch (EmployeeNotFoundException e) {
            assertEquals("Employee not found for this id:1",e.getMessage());
        }
        verify(employeeRepository, times(1)).findById(Mockito.anyInt());
    }
}
