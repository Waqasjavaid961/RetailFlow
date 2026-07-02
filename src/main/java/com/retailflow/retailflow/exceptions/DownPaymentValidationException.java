package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class DownPaymentValidationException extends BaseExceptionHandler{
    public DownPaymentValidationException(String message) {
        super(message,HttpStatus.BAD_REQUEST);
    }
}
