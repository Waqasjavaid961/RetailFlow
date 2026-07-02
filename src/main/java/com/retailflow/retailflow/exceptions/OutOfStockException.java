package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when trying to decrease stock below the available quantity — returns 400 BAD REQUEST
public class OutOfStockException extends BaseExceptionHandler{
    public OutOfStockException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

