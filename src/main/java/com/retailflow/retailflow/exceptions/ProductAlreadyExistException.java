package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistException extends BaseExceptionHandler{
    public ProductAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
