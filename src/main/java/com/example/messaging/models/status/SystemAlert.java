package com.example.messaging.models.status;

import com.example.messaging.models.enums.AlertType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class SystemAlert {
    @JsonProperty("alert_type")
    private final AlertType alertType;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("threshold_value")
    private final double thresholdValue;

    @JsonProperty("current_value")
    private final double currentValue;

    @JsonProperty("is_critical")
    private final boolean isCritical;

    private SystemAlert(Builder builder) {
        this.alertType = builder.alertType;
        this.message = builder.message != null ? builder.message : builder.alertType.getDescription();
        this.description = builder.alertType.getDescription();
        this.timestamp = Instant.now();
        this.thresholdValue = builder.thresholdValue;
        this.currentValue = builder.currentValue;
        this.isCritical = builder.alertType.isSystemCritical();
    }

    // Getters
    public AlertType getAlertType() { return alertType; }
    public String getMessage() { return message; }
    public String getDescription() { return description; }
    public Instant getTimestamp() { return timestamp; }
    public double getThresholdValue() { return thresholdValue; }
    public double getCurrentValue() { return currentValue; }
    public boolean isCritical() { return isCritical; }

    // Helper methods
    public boolean isThresholdExceeded() {
        return currentValue > thresholdValue;
    }

    public double getDeviationPercentage() {
        if (thresholdValue == 0) return 0;
        return ((currentValue - thresholdValue) / thresholdValue) * 100;
    }

    public String getFormattedMessage() {
        return String.format("%s - Current: %.2f, Threshold: %.2f (%.1f%% deviation)",
                message, currentValue, thresholdValue, getDeviationPercentage());
    }

    public static class Builder {
        private AlertType alertType;
        private String message;
        private double thresholdValue;
        private double currentValue;

        public Builder alertType(AlertType alertType) {
            this.alertType = alertType;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder thresholdValue(double thresholdValue) {
            this.thresholdValue = thresholdValue;
            return this;
        }

        public Builder currentValue(double currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public SystemAlert build() {
            if (alertType == null) {
                throw new IllegalStateException("AlertType must be specified");
            }
            return new SystemAlert(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
