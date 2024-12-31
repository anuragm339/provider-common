package com.example.messaging.models.enums;

public enum ResetType {
    FULL("FULL"),       // Complete reset of consumer data
    PARTIAL("PARTIAL"); // Reset from specific offset

    private final String value;

    ResetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ResetType fromString(String value) {
        for (ResetType type : ResetType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown reset type: " + value);
    }
}
