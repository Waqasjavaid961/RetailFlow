package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyRefundException extends BaseExceptionHandler{
    public AlreadyRefundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
