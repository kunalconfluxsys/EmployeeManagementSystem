package com.example.employee_sytem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found.
 * <p>
 * This exception is used to indicate that the requested resource
 * (e.g., an employee) could not be located in the system.
 * When thrown, it returns a 404 Not Found HTTP status code.
 * </p>
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ResourceNotFoundException(String message){

        super(message);

    }

}
