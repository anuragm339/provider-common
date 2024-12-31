package com.example.messaging.exceptions;

public class AuthenticationException extends MessageException {
    public AuthenticationException(String message) {
        super(message, ErrorCode.AUTHENTICATION_FAILED, false);
    }
}
