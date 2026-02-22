package com.example.restaurant.service;

/**
 * Runtime wrapper for SQL errors and other data-access problems.
 * Services will wrap SQLException (or other checked exceptions) in this class
 * so controllers don't have to declare checked exceptions.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataAccessException(String message) {
        super(message);
    }
}

