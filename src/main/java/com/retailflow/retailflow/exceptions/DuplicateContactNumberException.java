package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when a store is registered with a contact number that already exists — returns 409 CONFLICT
public class DuplicateContactNumberException extends BaseExceptionHandler {
    public DuplicateContactNumberException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

