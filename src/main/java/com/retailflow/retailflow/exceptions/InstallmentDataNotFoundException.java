package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class InstallmentDataNotFoundException extends BaseExceptionHandler {
    public InstallmentDataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
