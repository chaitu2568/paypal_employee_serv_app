package com.paypal.bfs.test.employeeserv.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.util.EntityMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    private EntityMapper entityMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        entityMapper = new EntityMapper();
    }

    @Override
    public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        com.paypal.bfs.test.employeeserv.model.Employee existingEmployee = employeeRepository
            .findByFirstNameAndLastNameAndDob(employee.getFirstName(), employee.getLastName(),
                employee.getDateOfBirth());
        if ((existingEmployee != null && existingEmployee.getId() <= 0) || existingEmployee == null) {
            com.paypal.bfs.test.employeeserv.model.Employee employeeEntity = entityMapper.toDbEntity(employee);
            employeeEntity = employeeRepository.save(employeeEntity);
            logger.debug("Successfully saved Employee with Id: {}", employeeEntity.getId());
            return entityMapper.fromDbEntity(employeeEntity);
        } else {
            logger.info("Existing Employee:{}", existingEmployee.toString());
            throw new EmployeeAlreadyExistsException("Employee already exists with the same details");
        }
    }

    @Override
    public Employee findEmployeeById(Integer id) throws EmployeeNotFoundException {
        Optional<com.paypal.bfs.test.employeeserv.model.Employee> employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isPresent()) {
            logger.debug("Employee: {}", employeeEntity.get().toString());
            Employee employeeResp = entityMapper.fromDbEntity(employeeEntity.get());
            return employeeResp;
        } else {
            logger.warn("Employee doesn't exist with id: {}", id);
            throw new EmployeeNotFoundException("Employee not found for this id:" + id);
        }
    }
}
