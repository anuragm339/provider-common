package com.example.messaging.models;

public enum MessageState {
    PENDING,    // Initial state
    SENT,       // Sent to consumer
    DELIVERED,  // Acknowledged by consumer
    FAILED      // Failed to deliver
}


