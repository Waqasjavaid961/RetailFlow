package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class StoreAlreadyExistException extends BaseExceptionHandler {
    public StoreAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
