package com.example.messaging.exceptions;

public enum ErrorCode {
    // Connection related
    CONNECTION_LOST(1000),
    CONNECTION_TIMEOUT(1001),

    // Processing related
    PROCESSING_ERROR(2000),
    VALIDATION_ERROR(2001),
    SERIALIZATION_ERROR(2002),

    // System related
    RESOURCE_EXHAUSTED(3000),
    INTERNAL_ERROR(3001),

    // Data related
    DATA_NOT_FOUND(4000),
    INVALID_OFFSET(4001),

    // Security related
    UNAUTHORIZED_CONSUMER(5000),
    AUTHENTICATION_FAILED(5001),
    INVALID_CONSUMER_STATE(5002),
    INVALID_CREDENTIALS(5003),
    MAX_CONNECTIONS_EXCEEDED(5004);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
