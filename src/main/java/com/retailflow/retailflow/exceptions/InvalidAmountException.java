package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAmountException extends BaseExceptionHandler{
    public InvalidAmountException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
