package com.example.messaging.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class MessageAck {
    @JsonProperty("msg_offset")
    private final long msgOffset;

    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("ack_type")
    private final AckType ackType;

    @JsonProperty("ack_time")
    private final Instant ackTime;

    @JsonProperty("processing_time_ms")
    private final long processingTimeMs;

    // Batch related fields
    @JsonProperty("batch_id")
    private final String batchId;

    @JsonProperty("sequence_in_batch")
    private final int sequenceInBatch;

    @JsonProperty("batch_size")
    private final int batchSize;

    private MessageAck(Builder builder) {
        this.msgOffset = builder.msgOffset;
        this.consumerId = builder.consumerId;
        this.ackType = builder.ackType;
        this.ackTime = builder.ackTime;
        this.processingTimeMs = builder.processingTimeMs;
        this.batchId = builder.batchId;
        this.sequenceInBatch = builder.sequenceInBatch;
        this.batchSize = builder.batchSize;
    }

    // Existing getters
    public long getMsgOffset() { return msgOffset; }
    public String getConsumerId() { return consumerId; }
    public AckType getAckType() { return ackType; }
    public Instant getAckTime() { return ackTime; }
    public long getProcessingTimeMs() { return processingTimeMs; }

    // New batch-related getters
    public String getBatchId() { return batchId; }
    public int getSequenceInBatch() { return sequenceInBatch; }
    public int getBatchSize() { return batchSize; }

    // Helper methods for batch operations
    public boolean isPartOfBatch() {
        return batchId != null;
    }

    public boolean isLastInBatch() {
        return isPartOfBatch() && sequenceInBatch == batchSize - 1;
    }

    public boolean isFirstInBatch() {
        return isPartOfBatch() && sequenceInBatch == 0;
    }

    public static class Builder {
        private long msgOffset;
        private String consumerId;
        private AckType ackType;
        private Instant ackTime;
        private long processingTimeMs;
        private String batchId;
        private int sequenceInBatch;
        private int batchSize;

        // Existing builder methods
        public Builder msgOffset(long msgOffset) {
            this.msgOffset = msgOffset;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder ackType(AckType ackType) {
            this.ackType = ackType;
            return this;
        }

        public Builder processingTimeMs(long processingTimeMs) {
            this.processingTimeMs = processingTimeMs;
            return this;
        }

        // New batch-related builder methods
        public Builder batchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public Builder sequenceInBatch(int sequenceInBatch) {
            this.sequenceInBatch = sequenceInBatch;
            return this;
        }

        public Builder batchSize(int batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public MessageAck build() {
            this.ackTime = Instant.now();
            return new MessageAck(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
