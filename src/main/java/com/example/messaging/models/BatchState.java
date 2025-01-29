package com.example.messaging.models;

public enum BatchState {
    PARTIAL("PARTIAL"),             // Some messages in batch acknowledged
    COMPLETE("COMPLETE"),           // All messages successfully acknowledged
    PARTIAL_FAILED("PARTIAL_FAILED"),
    PENDING("Pending"); // Some messages failed, might need retry

    private final String value;

    BatchState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BatchState fromString(String value) {
        for (BatchState state : BatchState.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown batch state: " + value);
    }
}
