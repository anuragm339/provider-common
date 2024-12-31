package com.example.messaging.models.response;

import com.example.messaging.models.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class StatusResponse {
    @JsonProperty("request_id")
    private final String requestId;

    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("status")
    private final ResponseStatus status;

    @JsonProperty("current_offset")
    private final long currentOffset;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("message")
    private final String message;

    private StatusResponse(Builder builder) {
        this.requestId = builder.requestId;
        this.consumerId = builder.consumerId;
        this.status = builder.status;
        this.currentOffset = builder.currentOffset;
        this.timestamp = Instant.now();
        this.message = builder.message;
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getConsumerId() { return consumerId; }
    public ResponseStatus getStatus() { return status; }
    public long getCurrentOffset() { return currentOffset; }
    public Instant getTimestamp() { return timestamp; }
    public String getMessage() { return message; }

    public static class Builder {
        private String requestId;
        private String consumerId;
        private ResponseStatus status = ResponseStatus.OK;
        private long currentOffset;
        private String message;

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder status(ResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder currentOffset(long currentOffset) {
            this.currentOffset = currentOffset;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public StatusResponse build() {
            return new StatusResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
