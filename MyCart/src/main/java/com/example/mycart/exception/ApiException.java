package com.example.mycart.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
