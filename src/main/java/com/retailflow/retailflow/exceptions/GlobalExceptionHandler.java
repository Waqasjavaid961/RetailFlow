package com.retailflow.retailflow.exceptions;

import com.retailflow.retailflow.common.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Centralized exception handler for the entire application
// @ControllerAdvice intercepts exceptions thrown from any controller
// Instead of returning a 500 error, it catches our custom exceptions and sends a proper JSON response
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles all exceptions that extend BaseExceptionHandler (e.g., ProductNotFoundException, OutOfStockException)
    @ExceptionHandler(BaseExceptionHandler.class)
    public ResponseEntity<ResponseApi<String>>handleBase(BaseExceptionHandler baseExceptionHandler){
        return ResponseEntity.status(baseExceptionHandler.getStatus()).body(ResponseApi.error(baseExceptionHandler.getMessage()));
    }
}

