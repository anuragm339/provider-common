package com.example.messaging.constants;

public final class MessageConstants {
    // Batch related constants
    public static final int MAX_BATCH_RETRIES = 3;
    public static final int MAX_BATCH_SIZE = 1000;
    public static final int MIN_BATCH_SIZE = 1;
    public static final long BATCH_TIMEOUT_MS = 5000; // 5 seconds

    // Retry related constants
    public static final long RETRY_INITIAL_DELAY_MS = 1000;  // 1 second
    public static final long RETRY_MAX_DELAY_MS = 300000;    // 5 minutes
    public static final int MAX_RETRY_ATTEMPTS = 3;

    // General message constants
    public static final int MAX_MESSAGE_SIZE_BYTES = 1024 * 1024; // 1MB
    public static final long MESSAGE_TTL_MS = 24 * 60 * 60 * 1000; // 24 hours

    private MessageConstants() {
        // Prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }
}
