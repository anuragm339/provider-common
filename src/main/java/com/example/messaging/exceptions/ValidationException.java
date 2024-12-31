package com.example.messaging.exceptions;

public class ValidationException extends MessageException {
    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_ERROR, false); // Validation errors are not retryable
    }

    public ValidationException(String message, Throwable cause) {
        super(message, ErrorCode.VALIDATION_ERROR, false, cause);
    }
}
