package com.example.messaging.models.status;

import com.example.messaging.models.enums.*;
import com.example.messaging.models.health.SystemState;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

public class SystemStatus {
    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("system_state")
    private final SystemState systemState;

    // Message Stats
    @JsonProperty("total_messages_processed")
    private final long totalMessagesProcessed;

    @JsonProperty("current_message_rate")
    private final long currentMessageRate;

    @JsonProperty("total_active_consumers")
    private final long totalActiveConsumers;

    // Error Stats
    @JsonProperty("failed_messages")
    private final long failedMessages;

    @JsonProperty("retry_count")
    private final long retryCount;

    // Processing Stats
    @JsonProperty("oldest_unprocessed_message_age_ms")
    private final long oldestUnprocessedMessageAge;

    @JsonProperty("average_processing_time_ms")
    private final long averageProcessingTime;

    @JsonProperty("alerts")
    private final List<SystemAlert> activeAlerts;

    private SystemStatus(Builder builder) {
        this.timestamp = Instant.now();
        this.systemState = builder.systemState;
        this.totalMessagesProcessed = builder.totalMessagesProcessed;
        this.currentMessageRate = builder.currentMessageRate;
        this.totalActiveConsumers = builder.totalActiveConsumers;
        this.failedMessages = builder.failedMessages;
        this.retryCount = builder.retryCount;
        this.oldestUnprocessedMessageAge = builder.oldestUnprocessedMessageAge;
        this.averageProcessingTime = builder.averageProcessingTime;
        this.activeAlerts = List.copyOf(builder.activeAlerts);
    }

    // Getters
    public Instant getTimestamp() { return timestamp; }
    public SystemState getSystemState() { return systemState; }
    public long getTotalMessagesProcessed() { return totalMessagesProcessed; }
    public long getCurrentMessageRate() { return currentMessageRate; }
    public long getTotalActiveConsumers() { return totalActiveConsumers; }
    public long getFailedMessages() { return failedMessages; }
    public long getRetryCount() { return retryCount; }
    public long getOldestUnprocessedMessageAge() { return oldestUnprocessedMessageAge; }
    public long getAverageProcessingTime() { return averageProcessingTime; }
    public List<SystemAlert> getActiveAlerts() { return activeAlerts; }

    public static class Builder {
        private SystemState systemState = SystemState.HEALTHY;
        private long totalMessagesProcessed;
        private long currentMessageRate;
        private long totalActiveConsumers;
        private long failedMessages;
        private long retryCount;
        private long oldestUnprocessedMessageAge;
        private long averageProcessingTime;
        private List<SystemAlert> activeAlerts = List.of();

        public Builder systemState(SystemState systemState) {
            this.systemState = systemState;
            return this;
        }

        public Builder totalMessagesProcessed(long totalMessagesProcessed) {
            this.totalMessagesProcessed = totalMessagesProcessed;
            return this;
        }

        public Builder currentMessageRate(long currentMessageRate) {
            this.currentMessageRate = currentMessageRate;
            return this;
        }

        public Builder totalActiveConsumers(long totalActiveConsumers) {
            this.totalActiveConsumers = totalActiveConsumers;
            return this;
        }

        public Builder failedMessages(long failedMessages) {
            this.failedMessages = failedMessages;
            return this;
        }

        public Builder retryCount(long retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public Builder oldestUnprocessedMessageAge(long oldestUnprocessedMessageAge) {
            this.oldestUnprocessedMessageAge = oldestUnprocessedMessageAge;
            return this;
        }

        public Builder averageProcessingTime(long averageProcessingTime) {
            this.averageProcessingTime = averageProcessingTime;
            return this;
        }

        public Builder activeAlerts(List<SystemAlert> activeAlerts) {
            this.activeAlerts = activeAlerts;
            return this;
        }

        public SystemStatus build() {
            return new SystemStatus(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
