package com.example.mycart.exception;


import com.example.mycart.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.mycart.constants.Constants.STATUS;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,Object>> constraintViolationExceptionHandler(ConstraintViolationException exception)
    {
        var errors = new HashMap<String,Object>();

        exception.getConstraintViolations().forEach(error-> errors.put(error.getPropertyPath().toString(),error.getMessage()));

        errors.put(STATUS, false);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> exceptionHandler(Exception exception)
    {
        var errors = new HashMap<String,Object>();

        errors.put("message", "internal server error. try after some time");

        errors.put(STATUS, false);

        log.error(exception.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
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
//    org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "inventory_product_id_key"
}
