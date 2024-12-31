package com.example.messaging.models;

public enum MessageType {
    RESET("RESET"),            // Reset command for client
    MESSAGE("MESSAGE"),        // Regular message
    READY("READY"),           // Ready signal
    BOOTSTRAP("BOOTSTRAP"),    // Bootstrap initiation
    ACK("ACK");              // Acknowledgment

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static MessageType fromString(String type) {
        for (MessageType mt : MessageType.values()) {
            if (mt.type.equalsIgnoreCase(type)) {
                return mt;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + type);
    }
}
