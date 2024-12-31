package com.example.messaging.models.enums;

public enum AlertType {
    HIGH_LATENCY("HIGH_LATENCY", "High message processing latency detected"),
    HIGH_ERROR_RATE("HIGH_ERROR_RATE", "Elevated error rate observed"),
    CONSUMER_LAG("CONSUMER_LAG", "Consumer falling behind message rate"),
    RESOURCE_CONSTRAINT("RESOURCE_CONSTRAINT", "System resource constraints detected"),
    DISK_USAGE("DISK_USAGE", "High disk usage detected"),
    QUEUE_DEPTH("QUEUE_DEPTH", "Message queue depth exceeding threshold"),
    CONSUMER_DISCONNECTED("CONSUMER_DISCONNECTED", "Consumer connection lost"),
    RETRY_THRESHOLD("RETRY_THRESHOLD", "High message retry rate detected");

    private final String value;
    private final String description;

    AlertType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AlertType fromString(String value) {
        for (AlertType type : AlertType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown alert type: " + value);
    }

    public boolean isSystemCritical() {
        return this == HIGH_ERROR_RATE ||
                this == RESOURCE_CONSTRAINT ||
                this == DISK_USAGE;
    }
}
