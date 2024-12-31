package com.example.messaging.models.health;

public enum AlertType {
    HIGH_LATENCY("HIGH_LATENCY"),
    HIGH_ERROR_RATE("HIGH_ERROR_RATE"),
    CONSUMER_LAG("CONSUMER_LAG"),
    RESOURCE_CONSTRAINT("RESOURCE_CONSTRAINT");

    private final String value;

    AlertType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AlertType fromString(String value) {
        for (AlertType type : AlertType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown alert type: " + value);
    }
}
