package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when a product is not found in the database — returns 404 NOT FOUND
public class ProductNotFoundException extends BaseExceptionHandler{
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

