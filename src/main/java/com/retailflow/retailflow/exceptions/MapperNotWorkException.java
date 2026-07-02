package com.retailflow.retailflow.exceptions;

import org.springframework.http.HttpStatus;

public class MapperNotWorkException extends BaseExceptionHandler{
    public MapperNotWorkException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
