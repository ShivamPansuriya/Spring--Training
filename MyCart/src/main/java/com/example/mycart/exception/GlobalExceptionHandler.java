package com.example.mycart.exception;


import com.example.mycart.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

import static com.example.mycart.constants.Constants.STATUS;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,Object>> constraintViolationExceptionHandler(ConstraintViolationException exception)
    {
        var errors = new HashMap<String,Object>();

        exception.getConstraintViolations().forEach(error-> errors.put(error.getPropertyPath().toString(),error.getMessage()));

        errors.put(STATUS, false);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getCause().getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
