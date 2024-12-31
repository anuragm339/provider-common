package com.example.messaging.status

import com.example.messaging.models.enums.AlertType
import com.example.messaging.models.health.SystemState
import com.example.messaging.models.status.SystemAlert
import com.example.messaging.models.status.SystemStatus
import spock.lang.Specification
import spock.lang.Unroll

class SystemStatusSpec extends Specification {

    def "should create SystemStatus with basic metrics"() {
        when:
        def status = SystemStatus.builder()
                .systemState(SystemState.HEALTHY)
                .totalMessagesProcessed(1000L)
                .currentMessageRate(100L)
                .totalActiveConsumers(5L)
                .build()

        then:
        status.systemState == SystemState.HEALTHY
        status.totalMessagesProcessed == 1000L
        status.currentMessageRate == 100L
        status.totalActiveConsumers == 5L
        status.timestamp != null
    }

    def "should create SystemStatus with alerts"() {
        given:
        def alert = SystemAlert.builder()
                .alertType(AlertType.HIGH_LATENCY)
                .message("High processing time detected")
                .thresholdValue(100.0)
                .currentValue(150.0)
                .build()

        when:
        def status = SystemStatus.builder()
                .systemState(SystemState.DEGRADED)
                .activeAlerts([alert])
                .build()

        then:
        status.systemState == SystemState.DEGRADED
        status.activeAlerts.size() == 1
        status.activeAlerts[0].alertType == AlertType.HIGH_LATENCY
        status.activeAlerts[0].message == "High processing time detected"
    }

    @Unroll
    def "should create SystemAlert for #alertType"() {
        when:
        def alert = SystemAlert.builder()
                .alertType(alertType)
                .message(message)
                .thresholdValue(threshold)
                .currentValue(current)
                .build()

        then:
        alert.alertType == alertType
        alert.message == message
        alert.thresholdValue == threshold
        alert.currentValue == current
        alert.timestamp != null

        where:
        alertType                    | message              | threshold | current
        AlertType.HIGH_LATENCY      | "High latency"       | 100.0     | 150.0
        AlertType.HIGH_ERROR_RATE   | "High errors"        | 0.01      | 0.05
        AlertType.CONSUMER_LAG      | "Consumer lagging"   | 1000.0    | 5000.0
    }

    def "should handle SystemState conversion"() {
        expect:
        SystemState.fromString("HEALTHY") == SystemState.HEALTHY
        SystemState.fromString("DEGRADED") == SystemState.DEGRADED
        SystemState.fromString("CRITICAL") == SystemState.CRITICAL

        when:
        SystemState.fromString("INVALID")

        then:
        thrown(IllegalArgumentException)
    }

    def "should handle AlertType conversion"() {
        expect:
        AlertType.fromString("HIGH_LATENCY") == AlertType.HIGH_LATENCY
        AlertType.fromString("HIGH_ERROR_RATE") == AlertType.HIGH_ERROR_RATE

        when:
        AlertType.fromString("INVALID")

        then:
        thrown(IllegalArgumentException)
    }
}
