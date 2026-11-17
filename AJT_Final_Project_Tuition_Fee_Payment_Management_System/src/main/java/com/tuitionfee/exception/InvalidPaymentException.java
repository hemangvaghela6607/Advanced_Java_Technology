package com.tuitionfee.exception;

/**
 * Exception thrown when a payment amount is invalid or exceeds the remaining balance.
 */
public class InvalidPaymentException extends AppException {

    private static final long serialVersionUID = 1L;

    public InvalidPaymentException(String message) {
        super(message);
    }
}
