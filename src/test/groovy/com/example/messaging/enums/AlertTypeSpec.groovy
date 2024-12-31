package com.example.messaging.enums

import com.example.messaging.models.enums.AlertType
import spock.lang.Specification
import spock.lang.Unroll

class AlertTypeSpec extends Specification {

    @Unroll
    def "should have correct description for #alertType"() {
        expect:
        alertType.description != null
        alertType.description == expectedDescription

        where:
        alertType                        | expectedDescription
        AlertType.HIGH_LATENCY | "High message processing latency detected"
        AlertType.HIGH_ERROR_RATE       | "Elevated error rate observed"
        AlertType.CONSUMER_LAG          | "Consumer falling behind message rate"
        AlertType.RESOURCE_CONSTRAINT   | "System resource constraints detected"
    }

    def "should convert from string correctly"() {
        expect:
        AlertType.fromString("HIGH_LATENCY") == AlertType.HIGH_LATENCY
        AlertType.fromString("high_latency") == AlertType.HIGH_LATENCY
        AlertType.fromString("HIGH_ERROR_RATE") == AlertType.HIGH_ERROR_RATE

        when:
        AlertType.fromString("INVALID_TYPE")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Unknown alert type: INVALID_TYPE"
    }

    @Unroll
    def "should correctly identify system critical alerts for #alertType"() {
        expect:
        alertType.systemCritical == isCritical

        where:
        alertType                      | isCritical
        AlertType.HIGH_ERROR_RATE     | true
        AlertType.RESOURCE_CONSTRAINT | true
        AlertType.DISK_USAGE         | true
        AlertType.HIGH_LATENCY       | false
        AlertType.CONSUMER_LAG       | false
        AlertType.QUEUE_DEPTH        | false
    }

    def "all alert types should have unique values"() {
        when:
        def values = AlertType.values()*.value as Set

        then:
        values.size() == AlertType.values().length
    }

    def "all alert types should have non-empty descriptions"() {
        expect:
        AlertType.values().every { it.description && !it.description.isEmpty() }
    }
}
