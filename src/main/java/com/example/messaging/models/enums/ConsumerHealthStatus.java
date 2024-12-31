package com.example.messaging.models.enums;

public enum ConsumerHealthStatus {
    HEALTHY("HEALTHY"),           // Processing messages normally
    DEGRADED("DEGRADED"),        // Processing but with issues
    UNHEALTHY("UNHEALTHY"),      // Not processing messages
    DISCONNECTED("DISCONNECTED"); // Not connected

    private final String value;

    ConsumerHealthStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConsumerHealthStatus fromString(String value) {
        for (ConsumerHealthStatus status : ConsumerHealthStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown health status: " + value);
    }
}
