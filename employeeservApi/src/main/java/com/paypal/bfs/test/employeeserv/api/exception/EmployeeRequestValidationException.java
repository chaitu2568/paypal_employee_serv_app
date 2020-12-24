package com.paypal.bfs.test.employeeserv.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeRequestValidationException extends Exception {
    private static final long serialVersionUID = 1L;
    private List<String> errorMessageList;

    public EmployeeRequestValidationException(List<String> errorMessageList) {
        super(errorMessageList.toString());
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }
}
