package com.example.messaging.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class RetryMessage {
    @JsonProperty("msg_offset")
    private final long msgOffset;

    @JsonProperty("retry_count")
    private final int retryCount;

    @JsonProperty("last_retry_utc")
    private final Instant lastRetryUtc;

    @JsonProperty("next_retry_utc")
    private final Instant nextRetryUtc;

    @JsonProperty("error_reason")
    private final String errorReason;

    // For monitoring
    @JsonProperty("first_failure_utc")
    private final Instant firstFailureUtc;

    private RetryMessage(Builder builder) {
        this.msgOffset = builder.msgOffset;
        this.retryCount = builder.retryCount;
        this.lastRetryUtc = builder.lastRetryUtc;
        this.nextRetryUtc = builder.nextRetryUtc;
        this.errorReason = builder.errorReason;
        this.firstFailureUtc = builder.firstFailureUtc;
    }

    // Getters
    public long getMsgOffset() { return msgOffset; }
    public int getRetryCount() { return retryCount; }
    public Instant getLastRetryUtc() { return lastRetryUtc; }
    public Instant getNextRetryUtc() { return nextRetryUtc; }
    public String getErrorReason() { return errorReason; }
    public Instant getFirstFailureUtc() { return firstFailureUtc; }

    // Calculate next retry time based on retry count
    public static Instant calculateNextRetryTime(int retryCount) {
        long delaySeconds;
        if (retryCount < 3) {
            delaySeconds = 10;  // First 3 retries: 10 seconds
        } else if (retryCount < 10) {
            delaySeconds = 60;  // Next 7 retries: 1 minute
        } else {
            delaySeconds = 300; // After 10 retries: 5 minutes
        }
        return Instant.now().plusSeconds(delaySeconds);
    }

    public static class Builder {
        private long msgOffset;
        private int retryCount;
        private Instant lastRetryUtc;
        private Instant nextRetryUtc;
        private String errorReason;
        private Instant firstFailureUtc;

        public Builder msgOffset(long msgOffset) {
            this.msgOffset = msgOffset;
            return this;
        }

        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            this.nextRetryUtc = calculateNextRetryTime(retryCount);
            return this;
        }

        public Builder lastRetryUtc(Instant lastRetryUtc) {
            this.lastRetryUtc = lastRetryUtc;
            return this;
        }

        public Builder errorReason(String errorReason) {
            this.errorReason = errorReason;
            return this;
        }

        public Builder firstFailureUtc(Instant firstFailureUtc) {
            this.firstFailureUtc = firstFailureUtc;
            return this;
        }

        public RetryMessage build() {
            if (firstFailureUtc == null) {
                firstFailureUtc = Instant.now();
            }
            return new RetryMessage(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
