package com.example.messaging.models.health;


public enum SystemState {
    HEALTHY("HEALTHY"),             // All working normally
    DEGRADED("DEGRADED"),          // Some issues but functioning
    CRITICAL("CRITICAL"),          // Serious issues detected
    MAINTENANCE("MAINTENANCE");     // Under maintenance

    private final String value;

    SystemState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SystemState fromString(String value) {
        for (SystemState state : SystemState.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown system state: " + value);
    }
}
