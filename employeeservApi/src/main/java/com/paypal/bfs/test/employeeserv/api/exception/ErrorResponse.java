package com.paypal.bfs.test.employeeserv.api.exception;

public class ErrorResponse {
    private String error_message;
    private String details;

    public ErrorResponse(String error_message, String details) {
        this.error_message = error_message;
        this.details = details;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
