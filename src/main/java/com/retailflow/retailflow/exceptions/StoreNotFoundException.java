package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when a store ID or name doesn't exist in the database — returns 404 NOT FOUND
public class StoreNotFoundException extends BaseExceptionHandler{
    public StoreNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

