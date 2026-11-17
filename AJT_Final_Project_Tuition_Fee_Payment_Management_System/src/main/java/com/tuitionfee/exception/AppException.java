package com.tuitionfee.exception;

/**
 * Base custom runtime exception for Tuition Fee Management System.
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
