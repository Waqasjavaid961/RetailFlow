package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class SaleNotFoundException extends BaseExceptionHandler{
    public SaleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
