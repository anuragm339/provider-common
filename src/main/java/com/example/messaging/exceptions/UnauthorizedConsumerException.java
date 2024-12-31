package com.example.messaging.exceptions;

public class UnauthorizedConsumerException extends MessageException {
    public UnauthorizedConsumerException(String consumerId) {
        super(
                String.format("Unauthorized consumer attempted to connect: %s", consumerId),
                ErrorCode.UNAUTHORIZED_CONSUMER,
                false  // Not retryable - needs proper credentials
        );
    }
}
