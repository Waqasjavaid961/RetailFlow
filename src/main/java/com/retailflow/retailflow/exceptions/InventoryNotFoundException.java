package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

// Thrown when no inventory record is found for a given product — returns 404 NOT FOUND
public class InventoryNotFoundException extends BaseExceptionHandler{
    public InventoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

