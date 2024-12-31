package com.example.messaging.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class DLCEntry {
    @JsonProperty("msg_offset")
    private final long msgOffset;

    @JsonProperty("total_retry_count")
    private final int totalRetryCount;

    @JsonProperty("first_failure_utc")
    private final Instant firstFailureUtc;

    @JsonProperty("last_failure_utc")
    private final Instant lastFailureUtc;

    @JsonProperty("error_pattern")
    private final String errorPattern;

    @JsonProperty("consecutive_failures")
    private final int consecutiveFailures;

    @JsonProperty("monitoring_status")
    private final DLCStatus monitoringStatus;

    private DLCEntry(Builder builder) {
        this.msgOffset = builder.msgOffset;
        this.totalRetryCount = builder.totalRetryCount;
        this.firstFailureUtc = builder.firstFailureUtc;
        this.lastFailureUtc = builder.lastFailureUtc;
        this.errorPattern = builder.errorPattern;
        this.consecutiveFailures = builder.consecutiveFailures;
        this.monitoringStatus = builder.monitoringStatus;
    }

    // Getters
    public long getMsgOffset() { return msgOffset; }
    public int getTotalRetryCount() { return totalRetryCount; }
    public Instant getFirstFailureUtc() { return firstFailureUtc; }
    public Instant getLastFailureUtc() { return lastFailureUtc; }
    public String getErrorPattern() { return errorPattern; }
    public int getConsecutiveFailures() { return consecutiveFailures; }
    public DLCStatus getMonitoringStatus() { return monitoringStatus; }

    public enum DLCStatus {
        MONITORING,         // Just monitoring
        WARNING,           // High retry count but still trying
        ALERT,            // Needs attention
        PATTERN_DETECTED  // Similar failures detected
    }

    public static class Builder {
        private long msgOffset;
        private int totalRetryCount;
        private Instant firstFailureUtc;
        private Instant lastFailureUtc;
        private String errorPattern;
        private int consecutiveFailures;
        private DLCStatus monitoringStatus = DLCStatus.MONITORING;

        public Builder msgOffset(long msgOffset) {
            this.msgOffset = msgOffset;
            return this;
        }

        public Builder totalRetryCount(int totalRetryCount) {
            this.totalRetryCount = totalRetryCount;
            return this;
        }

        public Builder firstFailureUtc(Instant firstFailureUtc) {
            this.firstFailureUtc = firstFailureUtc;
            return this;
        }

        public Builder lastFailureUtc(Instant lastFailureUtc) {
            this.lastFailureUtc = lastFailureUtc;
            return this;
        }

        public Builder errorPattern(String errorPattern) {
            this.errorPattern = errorPattern;
            return this;
        }

        public Builder consecutiveFailures(int consecutiveFailures) {
            this.consecutiveFailures = consecutiveFailures;
            return this;
        }

        public Builder monitoringStatus(DLCStatus monitoringStatus) {
            this.monitoringStatus = monitoringStatus;
            return this;
        }

        public DLCEntry build() {
            if (lastFailureUtc == null) {
                lastFailureUtc = Instant.now();
            }
            if (firstFailureUtc == null) {
                firstFailureUtc = lastFailureUtc;
            }
            return new DLCEntry(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
