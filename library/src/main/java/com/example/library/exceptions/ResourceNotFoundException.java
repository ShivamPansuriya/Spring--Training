package com.example.library.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String filed;
    private String filedName;
    private String resourceName;
    private Long filedId;

    public ResourceNotFoundException(){}

    public ResourceNotFoundException(String resourceName, String filed, String filedName) {
        super(String.format("%s is not found %s: %s", resourceName, filed, filedName));
        this.filed = filed;
        this.filedName = filedName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String resourceName, String filed, Long filedId) {
        super(String.format("%s is not found %s: %d", resourceName, filed, filedId));
        this.filed = filed;
        this.resourceName = resourceName;
        this.filedId = filedId;
    }
}
