package com.example.messaging.exceptions;

public class ProcessingException extends MessageException {
    public ProcessingException(String message, ErrorCode errorCode, boolean retryable) {
        super(message, errorCode, retryable);
    }

    public ProcessingException(String message, ErrorCode errorCode, boolean retryable, Throwable cause) {
        super(message, errorCode, retryable, cause);
    }
}
