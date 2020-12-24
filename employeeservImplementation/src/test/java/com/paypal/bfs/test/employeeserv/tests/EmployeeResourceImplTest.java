package com.paypal.bfs.test.employeeserv.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

public class EmployeeResourceImplTest extends EmployeeMock {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeResourceImplTest.class);

    public static final String BFS_EMPLOYEES_URL = "/v1/bfs/employees/";
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeResourceImpl employeeResourceImpl;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeResourceImpl).build();
    }

    @Test
    public void testEmployeeGetById() throws Exception {
        String empId = "123";
        Employee employee = mockEmployee();
        employee.setId(123);
        when(employeeService.findEmployeeById(Mockito.anyInt())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get(BFS_EMPLOYEES_URL + empId).accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.address.state").isNotEmpty());
    }

    @Test
    public void testEmployeeNotFoundById() throws Exception {
        String empId = "123";
        String expected = "Employee not found for this id:" + empId;
        when(employeeService.findEmployeeById(Mockito.anyInt()))
            .thenThrow(new EmployeeNotFoundException(expected));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BFS_EMPLOYEES_URL + empId).accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isNotFound()).andReturn();
        String actual = result.getResolvedException().getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveEmployee() throws Exception {

        Employee employee = mockEmployee();
        employee.setId(123);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post(BFS_EMPLOYEES_URL).content(asJsonString(employee))
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testSaveEmployeeWithInvalidInput() throws Exception {

        Employee employee = mockInvalidEmployee();

        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post(BFS_EMPLOYEES_URL).content(asJsonString(employee))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest()).andReturn();
        String actual = result.getResolvedException().getMessage();
        String expected = "[First Name: should not be empty, Last Name: should not be empty, Date of Birth: should be in YYYY-MM-DD format, Address Line1: should not be empty, City: should not be empty, State: should not be empty, Country: should not be empty, Zip Code: should not be empty]";
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveEmployeeWithExistingEmployee() throws Exception {
        String expected ="Employee already exists with the same details";
        when(employeeService.saveEmployee(any(Employee.class)))
            .thenThrow(new EmployeeAlreadyExistsException(expected));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BFS_EMPLOYEES_URL).content(asJsonString(mockEmployee()))
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isConflict()).andReturn();
        String actual = result.getResolvedException().getMessage();
        assertEquals(expected, actual);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
