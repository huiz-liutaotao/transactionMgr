package com.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {  

    @ExceptionHandler(IncidentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIncidentNotFound(
            IncidentNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(  
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",  
            ex.getMessage(),  
            request.getDescription(false)  
        );  
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);  
    }  

    @ExceptionHandler(Exception.class)  
    public ResponseEntity<ErrorResponse> handleGlobalException(  
            Exception ex, WebRequest request) {  
        
        ErrorResponse errorResponse = new ErrorResponse(  
            LocalDateTime.now(),  
            HttpStatus.INTERNAL_SERVER_ERROR.value(),  
            "Internal Server Error",  
            ex.getMessage(),  
            request.getDescription(false)  
        );  
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);  
    }  
}