package com.example.mycart.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){}

    public ResourceNotFoundException(String resourceName, String filed, String filedName) {
        super(String.format("%s is not found %s: %s", resourceName, filed, filedName));
    }

    public ResourceNotFoundException(String resourceName, String filed, Long filedId) {
        super(String.format("%s is not found %s: %d", resourceName, filed, filedId));
    }
}
