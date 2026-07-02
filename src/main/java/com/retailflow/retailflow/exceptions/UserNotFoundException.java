package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseExceptionHandler{
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
