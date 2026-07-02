package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when a store lookup by email fails (store not found for that email) — returns 400 BAD REQUEST
public class EmailNotFoundException extends BaseExceptionHandler{
    public EmailNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

