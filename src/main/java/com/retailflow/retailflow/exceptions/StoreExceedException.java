package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class StoreExceedException extends BaseExceptionHandler{
    public StoreExceedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
