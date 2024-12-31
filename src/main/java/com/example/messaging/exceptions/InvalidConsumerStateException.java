package com.example.messaging.exceptions;

public class InvalidConsumerStateException extends MessageException {
    public InvalidConsumerStateException(String consumerId, String currentState, String attemptedAction) {
        super(
                String.format("Consumer %s in state %s cannot perform action: %s",
                        consumerId, currentState, attemptedAction),
                ErrorCode.INVALID_CONSUMER_STATE,
                false
        );
    }
}
