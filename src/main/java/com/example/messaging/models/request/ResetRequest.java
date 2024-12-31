package com.example.messaging.models.request;

import com.example.messaging.models.enums.ResetType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetRequest extends BaseRequest {
    @JsonProperty("reset_type")
    private final ResetType resetType;

    private ResetRequest(Builder builder) {
        super(builder.requestId, builder.consumerId);
        this.resetType = builder.resetType;
    }

    public ResetType getResetType() { return resetType; }

    public static class Builder {
        private String requestId;
        private String consumerId;
        private ResetType resetType = ResetType.FULL;

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder resetType(ResetType resetType) {
            this.resetType = resetType;
            return this;
        }

        public ResetRequest build() {
            return new ResetRequest(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
