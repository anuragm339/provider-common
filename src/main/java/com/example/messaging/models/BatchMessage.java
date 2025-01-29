package com.example.messaging.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchMessage {
    private String batchId;
    private Long count;
    private List<Message> data;
    private String type;

    public BatchMessage() {
    }

    public BatchMessage(String batchId, Long count, List<Message> data, String type) {
        this.batchId = batchId;
        this.count = count;
        this.data = data;
        this.type = type;
    }

    public String getBatchId() {
        return batchId;
    }

    public Long getCount() {
        return count;
    }

    public List<Message> getData() {
        return data;
    }

    public String getType() {
        return type;
    }
}
