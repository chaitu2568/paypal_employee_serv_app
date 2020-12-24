package com.paypal.bfs.test.employeeserv.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeRequestValidationException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

public class RequestValidator {
    private static final Logger logger = LoggerFactory.getLogger(RequestValidator.class);

    private static final int MAX_LENGTH = 255;
    private static final String CITY = "City: ";
    private static final String STATE = "State: ";
    private static final String ADDRESS = "Address: ";
    private static final String COUNTRY = "Country: ";
    private static final String ZIP_CODE = "Zip Code: ";
    private static final String LAST_NAME = "Last Name: ";
    private static final String FIRST_NAME = "First Name: ";
    private static final String DATE_OF_BIRTH = "Date of Birth: ";
    private static final String ADDRESS_LINE1 = "Address Line1: ";
    private static final String ADDRESS_LINE2 = "Address Line2: ";
    private static final String NOT_EMPTY = "should not be empty";
    private static final String MAX_LENGTH_MESSAGE = "max length is 255";
    public static final String DD_FORMAT = "should be in YYYY-MM-DD format";
    private static final String ZIP_CODE_MAX_LENGTH_MESSAGE = "max length is 20";

    public static void checkEmployeeRequestValidations(Employee employee) throws EmployeeRequestValidationException {
        List<String> errorsMessageList = new ArrayList<>();

        if (isEmpty(employee.getFirstName())) {
            errorsMessageList.add(FIRST_NAME + NOT_EMPTY);
        } else if (isMaxLength(employee.getFirstName(), MAX_LENGTH)) {
            errorsMessageList.add(FIRST_NAME + MAX_LENGTH_MESSAGE);
        }

        if (isEmpty(employee.getLastName())) {
            errorsMessageList.add(LAST_NAME + NOT_EMPTY);
        } else if (isMaxLength(employee.getLastName(), MAX_LENGTH)) {
            errorsMessageList.add(LAST_NAME + MAX_LENGTH_MESSAGE);
        }

        if (isEmpty(employee.getDateOfBirth())) {
            errorsMessageList.add(DATE_OF_BIRTH + NOT_EMPTY);
        } else if (isInvalidDateFormat(employee.getDateOfBirth())) {
            errorsMessageList.add(DATE_OF_BIRTH + DD_FORMAT);
        }

        if (Objects.isNull(employee.getAddress())) {
            errorsMessageList.add(ADDRESS + NOT_EMPTY);
        } else {
            if (isEmpty(employee.getAddress().getLine1())) {
                errorsMessageList.add(ADDRESS_LINE1 + NOT_EMPTY);
            } else if (isMaxLength(employee.getAddress().getLine1(), MAX_LENGTH)) {
                errorsMessageList.add(ADDRESS_LINE1 + MAX_LENGTH_MESSAGE);
            }
            if (!isEmpty(employee.getAddress().getLine2()) && isMaxLength(employee.getAddress().getLine2(),
                MAX_LENGTH)) {
                errorsMessageList.add(ADDRESS_LINE2 + MAX_LENGTH_MESSAGE);
            }

            if (isEmpty(employee.getAddress().getCity())) {
                errorsMessageList.add(CITY + NOT_EMPTY);
            } else if (isMaxLength(employee.getAddress().getCity(), MAX_LENGTH)) {
                errorsMessageList.add(CITY + MAX_LENGTH_MESSAGE);
            }

            if (isEmpty(employee.getAddress().getState())) {
                errorsMessageList.add(STATE + NOT_EMPTY);
            } else if (isMaxLength(employee.getAddress().getState(), MAX_LENGTH)) {
                errorsMessageList.add(STATE + MAX_LENGTH_MESSAGE);
            }

            if (isEmpty(employee.getAddress().getCountry())) {
                errorsMessageList.add(COUNTRY + NOT_EMPTY);
            } else if (isMaxLength(employee.getAddress().getCountry(), MAX_LENGTH)) {
                errorsMessageList.add(COUNTRY + MAX_LENGTH_MESSAGE);
            }

            if (employee.getAddress().getZipCode() == null || isEmpty(String.valueOf(employee.getAddress().getZipCode()))) {
                errorsMessageList.add(ZIP_CODE + NOT_EMPTY);
            } else if (isMaxLength(String.valueOf(employee.getAddress().getZipCode()), 20)) {
                errorsMessageList.add(ZIP_CODE + ZIP_CODE_MAX_LENGTH_MESSAGE);
            }
        }

        if (errorsMessageList.size() > 0) {
            throw new EmployeeRequestValidationException(errorsMessageList);
        }
    }

    private static boolean isEmpty(String value) {
        return null == value || value.length() < 1;
    }

    private static boolean isMaxLength(String value, int maxLength) {
        return value.length() > maxLength;
    }

    private static boolean isInvalidDateFormat(String value) {
        String pattern = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        Matcher matcher = Pattern.compile(pattern).matcher(value);
        return !matcher.matches();
    }
}
