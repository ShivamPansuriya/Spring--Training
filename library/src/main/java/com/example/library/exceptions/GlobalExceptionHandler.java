package com.example.library.exceptions;

import com.example.library.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> constraintViolationExceptionHandler(ConstraintViolationException exception)
    {
        var errors = new HashMap<String,String>();

        exception.getConstraintViolations().forEach(error-> errors.put(error.getPropertyPath().toString(),error.getMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false),HttpStatus.NOT_FOUND);
    }
}
