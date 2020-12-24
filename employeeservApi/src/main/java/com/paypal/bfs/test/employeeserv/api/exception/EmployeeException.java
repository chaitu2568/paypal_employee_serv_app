package com.paypal.bfs.test.employeeserv.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmployeeException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmployeeException(String message) {
        super(message);
    }
}
