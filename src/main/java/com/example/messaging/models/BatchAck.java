package com.example.messaging.models;

import com.example.messaging.constants.MessageConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BatchAck {
    @JsonProperty("batch_id")
    private final String batchId;

    @JsonProperty("consumer_id")
    private final String consumerId;

    @JsonProperty("ack_time")
    private final Instant ackTime;

    @JsonProperty("acks")
    private final List<MessageAck> acks;

    @JsonProperty("batch_sequence")
    private final long batchSequence;

    @JsonProperty("batch_state")
    private final BatchState batchState;

    @JsonProperty("retry_count")
    private final int retryCount;

    @JsonProperty("failed_sequences")
    private final List<Integer> failedSequences;

    private BatchAck(Builder builder) {
        this.batchId = builder.batchId;
        this.consumerId = builder.consumerId;
        this.ackTime = Instant.now();
        this.acks = List.copyOf(builder.acks);
        this.batchSequence = builder.batchSequence;
        this.retryCount = builder.retryCount;

        // Calculate failed sequences
        this.failedSequences = calculateFailedSequences(builder.acks);

        // Determine batch state
        this.batchState = determineBatchState(builder.acks);
    }

    private List<Integer> calculateFailedSequences(List<MessageAck> acks) {
        return acks.stream()
                .filter(ack -> ack.getAckType() == AckType.FAILED)
                .map(MessageAck::getSequenceInBatch)
                .sorted()
                .collect(Collectors.toList());
    }

    private BatchState determineBatchState(List<MessageAck> acks) {
        if (acks.isEmpty()) {
            return BatchState.PARTIAL;
        }

        int expectedSize = acks.get(0).getBatchSize();
        boolean hasFailures = acks.stream()
                .anyMatch(ack -> ack.getAckType() == AckType.FAILED);

        if (hasFailures) {
            return BatchState.PARTIAL_FAILED;
        }

        return acks.size() == expectedSize ? BatchState.COMPLETE : BatchState.PARTIAL;
    }

    // Existing getters
    public String getBatchId() { return batchId; }
    public String getConsumerId() { return consumerId; }
    public Instant getAckTime() { return ackTime; }
    public List<MessageAck> getAcks() { return acks; }
    public long getBatchSequence() { return batchSequence; }

    // New getters
    public BatchState getBatchState() { return batchState; }
    public int getRetryCount() { return retryCount; }
    public List<Integer> getFailedSequences() { return failedSequences; }

    // Helper methods
    public boolean needsRetry() {
        return batchState == BatchState.PARTIAL_FAILED && retryCount < MessageConstants.MAX_BATCH_RETRIES;
    }

    public boolean isComplete() {
        return batchState == BatchState.COMPLETE;
    }

    public Set<Integer> getMissingSequences() {
        if (acks.isEmpty()) return Set.of();

        int expectedSize = acks.get(0).getBatchSize();
        Set<Integer> receivedSequences = acks.stream()
                .map(MessageAck::getSequenceInBatch)
                .collect(Collectors.toSet());

        return java.util.stream.IntStream.range(0, expectedSize)
                .filter(seq -> !receivedSequences.contains(seq))
                .boxed()
                .collect(Collectors.toSet());
    }

    public static class Builder {
        private String batchId;
        private String consumerId;
        private List<MessageAck> acks;
        private long batchSequence;
        private int retryCount;

        public Builder batchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder acks(List<MessageAck> acks) {
            this.acks = acks;
            return this;
        }

        public Builder batchSequence(long batchSequence) {
            this.batchSequence = batchSequence;
            return this;
        }

        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public BatchAck build() {
            return new BatchAck(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
