package com.example.messaging.models.health;

import com.example.messaging.models.enums.ConsumerHealthStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class ConsumerHealth {
    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("status")
    private final ConsumerHealthStatus status;

    @JsonProperty("last_active_time")
    private final Instant lastActiveTime;

    @JsonProperty("current_offset")
    private final long currentOffset;

    @JsonProperty("last_error")
    private final String lastError;

    private ConsumerHealth(Builder builder) {
        this.consumerId = builder.consumerId;
        this.status = builder.status;
        this.lastActiveTime = builder.lastActiveTime;
        this.currentOffset = builder.currentOffset;
        this.lastError = builder.lastError;
    }

    // Getters
    public String getConsumerId() { return consumerId; }
    public ConsumerHealthStatus getStatus() { return status; }
    public Instant getLastActiveTime() { return lastActiveTime; }
    public long getCurrentOffset() { return currentOffset; }
    public String getLastError() { return lastError; }

    public static class Builder {
        private String consumerId;
        private ConsumerHealthStatus status = ConsumerHealthStatus.HEALTHY;
        private Instant lastActiveTime;
        private long currentOffset;
        private String lastError;

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder status(ConsumerHealthStatus status) {
            this.status = status;
            return this;
        }

        public Builder lastActiveTime(Instant lastActiveTime) {
            this.lastActiveTime = lastActiveTime;
            return this;
        }

        public Builder currentOffset(long currentOffset) {
            this.currentOffset = currentOffset;
            return this;
        }

        public Builder lastError(String lastError) {
            this.lastError = lastError;
            return this;
        }

        public ConsumerHealth build() {
            if (lastActiveTime == null) {
                lastActiveTime = Instant.now();
            }
            return new ConsumerHealth(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
