package com.example.messaging.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public abstract class BaseRequest {
    @JsonProperty("request_id")
    private final String requestId;

    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    protected BaseRequest(String requestId, String consumerId) {
        this.requestId = requestId;
        this.consumerId = consumerId;
        this.timestamp = Instant.now();
    }

    public String getRequestId() { return requestId; }
    public String getConsumerId() { return consumerId; }
    public Instant getTimestamp() { return timestamp; }
}
