package com.example.messaging.models.health

import com.example.messaging.models.enums.ConsumerHealthStatus
import com.example.messaging.models.health.ConsumerHealth
import spock.lang.Specification
import spock.lang.Unroll
import java.time.Instant

class ConsumerHealthSpec extends Specification {

    @Unroll
    def "should create ConsumerHealth with status #status"() {
        given:
        def now = Instant.now()

        when:
        def health = ConsumerHealth.builder()
                .consumerId("consumer-1")
                .status(status)
                .lastActiveTime(now)
                .currentOffset(1000L)
                .lastError(error)
                .build()

        then:
        health.consumerId == "consumer-1"
        health.status == status
        health.lastActiveTime == now
        health.currentOffset == 1000L
        health.lastError == error

        where:
        status                          | error
        ConsumerHealthStatus.HEALTHY    | null
        ConsumerHealthStatus.DEGRADED   | "Processing slowly"
        ConsumerHealthStatus.UNHEALTHY  | "Connection issues"
    }

    def "should set default lastActiveTime if not provided"() {
        when:
        def health = ConsumerHealth.builder()
                .consumerId("consumer-1")
                .status(ConsumerHealthStatus.HEALTHY)
                .build()

        then:
        health.lastActiveTime != null
        health.lastActiveTime.isBefore(Instant.now().plusSeconds(1))
        health.lastActiveTime.isAfter(Instant.now().minusSeconds(1))
    }

    def "should handle ConsumerHealthStatus string conversion"() {
        expect:
        ConsumerHealthStatus.fromString("HEALTHY") == ConsumerHealthStatus.HEALTHY
        ConsumerHealthStatus.fromString("healthy") == ConsumerHealthStatus.HEALTHY
        ConsumerHealthStatus.fromString("DEGRADED") == ConsumerHealthStatus.DEGRADED
        ConsumerHealthStatus.fromString("UNHEALTHY") == ConsumerHealthStatus.UNHEALTHY
        ConsumerHealthStatus.fromString("DISCONNECTED") == ConsumerHealthStatus.DISCONNECTED

        when:
        ConsumerHealthStatus.fromString("INVALID")

        then:
        thrown(IllegalArgumentException)
    }
}
