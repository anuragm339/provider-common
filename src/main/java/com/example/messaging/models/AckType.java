package com.example.messaging.models;

public enum AckType {
    RECEIVED("RECEIVED"),       // Message received by consumer
    PROCESSED("PROCESSED"),     // Message successfully processed
    FAILED("FAILED"),          // Processing failed
    RESET_DONE("RESET_DONE"),  // Reset operation completed
    READY("READY");           // Consumer ready for messages

    private final String value;

    AckType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AckType fromString(String value) {
        for (AckType type : AckType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ack type: " + value);
    }
}
