package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Abstract base class for all custom exceptions in the application
// Every specific exception (like ProductNotFoundException) extends this class
// and passes its own HTTP status code, so GlobalExceptionHandler can return the right response
public abstract class BaseExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    protected BaseExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}

