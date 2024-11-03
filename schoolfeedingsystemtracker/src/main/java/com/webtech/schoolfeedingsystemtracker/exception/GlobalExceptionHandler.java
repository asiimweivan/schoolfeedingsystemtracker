package com.webtech.schoolfeedingsystemtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StackOverflowError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleStackOverflowError(StackOverflowError ex) {
        // Log error and return a meaningful response
        System.err.println("StackOverflowError occurred: " + ex.getMessage());
    }
}
