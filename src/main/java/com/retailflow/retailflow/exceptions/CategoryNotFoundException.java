package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when an invalid or unrecognized product category is passed in the request — returns 404 NOT FOUND
public class CategoryNotFoundException extends BaseExceptionHandler{
    public CategoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

