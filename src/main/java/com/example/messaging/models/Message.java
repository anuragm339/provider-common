package com.example.messaging.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @JsonProperty("msg_offset")
    private  long msgOffset;

    @JsonProperty("type")
    private  String type;

    @JsonProperty("created_utc")
    private  Instant createdUtc;

    @JsonProperty("data")
    private  byte[] data;

    @JsonProperty("state")
    private  MessageState state;

    @JsonProperty("retry_count")
    private  int retryCount;

    @JsonProperty("last_retry_utc")
    private  Instant lastRetryUtc;

    @JsonProperty("msg_key")
    private  String msgKey;

    public Message() {
    }

    private Message(Builder builder) {
        this.msgOffset = builder.msgOffset;
        this.type = builder.type;
        this.createdUtc = builder.createdUtc;
        this.data = builder.data != null ? builder.data.clone() : null;
        this.state = builder.state;
        this.retryCount = builder.retryCount;
        this.lastRetryUtc = builder.lastRetryUtc;
        this.msgKey=builder.msgKey;
    }

    // Getters

    public String getMsgKey() { return msgKey; }
    public long getMsgOffset() { return msgOffset; }
    public String getType() { return type; }
    public Instant getCreatedUtc() { return createdUtc; }
    public byte[] getData() { return data != null ? data.clone() : null; }
    public MessageState getState() { return state; }
    public int getRetryCount() { return retryCount; }
    public Instant getLastRetryUtc() { return lastRetryUtc; }

    /**
     * Calculate next retry delay in milliseconds using exponential backoff
     * Base delay is 1 second, doubles with each retry up to 1 minute
     */
    public long getNextRetryDelay() {
        long baseDelay = 1000; // 1 second
        long maxDelay = 60000; // 1 minute
        long delay = baseDelay * (1L << Math.min(retryCount, 6)); // 2^retryCount
        return Math.min(delay, maxDelay);
    }

    public static class Builder {
        private long msgOffset;
        private String type;
        private Instant createdUtc;
        private byte[] data;
        private MessageState state = MessageState.PENDING;
        private int retryCount = 0;
        private Instant lastRetryUtc;
        private String msgKey;

        public Builder msgOffset(long msgOffset) {
            this.msgOffset = msgOffset;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder createdUtc(Instant createdUtc) {
            this.createdUtc = createdUtc;
            return this;
        }

        public Builder data(byte[] data) {
            this.data = data != null ? data.clone() : null;
            return this;
        }

        public Builder state(MessageState state) {
            this.state = state;
            return this;
        }

        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public Builder lastRetryUtc(Instant lastRetryUtc) {
            this.lastRetryUtc = lastRetryUtc;
            return this;
        }

        public Builder msgKey(String msgKey) {
            this.msgKey = msgKey;
            return this;
        }

        public Message build() {
            if (createdUtc == null) {
                createdUtc = Instant.now();
            }
            return new Message(this);
        }
    }

    public static Builder from(Message existingMessage) {
        return new Builder()
                .msgOffset(existingMessage.getMsgOffset())
                .type(existingMessage.getType())
                .createdUtc(existingMessage.getCreatedUtc())
                .data(existingMessage.getData())
                .state(existingMessage.getState())
                .retryCount(existingMessage.getRetryCount())
                .lastRetryUtc(existingMessage.getLastRetryUtc())
                .msgKey(existingMessage.getMsgKey());
    }
    public static Builder builder() {
        return new Builder();
    }
}

