package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class PayementTypeException extends BaseExceptionHandler{
    public PayementTypeException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
