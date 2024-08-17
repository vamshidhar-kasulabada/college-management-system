package com.vamshidhar.cms.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Long id, String resourceName) {
        super("Resource Not Found " + resourceName + " ID: " + id);
    }
}
