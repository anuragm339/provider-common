package com.example.messaging.models.enums;

public enum ResponseStatus {
    OK("OK"),                   // Operation completed successfully
    ERROR("ERROR"),             // Operation failed
    IN_PROGRESS("IN_PROGRESS"), // Operation is being processed
    RESETTING("RESETTING");     // Reset operation in progress

    private final String value;

    ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ResponseStatus fromString(String value) {
        for (ResponseStatus status : ResponseStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown response status: " + value);
    }
}
