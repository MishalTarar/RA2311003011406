package com.test.scheduler.exception;

import com.test.scheduler.service.LoggingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LoggingService loggingService;

    public GlobalExceptionHandler(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {

        String errorMessage = "ERROR: " + ex.getMessage();

        loggingService.sendLog(
                "backend",
                "error",
                errorMessage
        );

        System.out.println("ERROR LOG: " + errorMessage);

        return "Something went wrong!";
    }
}