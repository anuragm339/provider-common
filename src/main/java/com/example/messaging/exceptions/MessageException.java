package com.example.messaging.exceptions;

public class MessageException extends RuntimeException {
    private final ErrorCode errorCode;
    private final boolean retryable;

    public MessageException(String message, ErrorCode errorCode, boolean retryable) {
        super(message);
        this.errorCode = errorCode;
        this.retryable = retryable;
    }

    public MessageException(String message, ErrorCode errorCode, boolean retryable, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.retryable = retryable;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }
}
