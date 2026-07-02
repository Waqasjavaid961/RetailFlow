package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseExceptionHandler{
    public DuplicateEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
