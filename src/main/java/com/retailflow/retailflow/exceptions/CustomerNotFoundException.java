package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseExceptionHandler{
    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
