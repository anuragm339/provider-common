package com.example.messaging.exceptions;

public class ConnectionException extends MessageException {
    public ConnectionException(String message, ErrorCode errorCode) {
        super(message, errorCode, true); // Connection issues are typically retryable
    }

    public ConnectionException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, true, cause);
    }
}




