package com.example.messaging.models

import com.example.messaging.models.AckType
import spock.lang.Specification
import spock.lang.Unroll

class AckTypeSpec extends Specification {

    @Unroll
    def "should convert string '#value' to AckType.#expected"() {
        expect:
        AckType.fromString(value) == expected

        where:
        value        | expected
        "RECEIVED"   | AckType.RECEIVED
        "PROCESSED"  | AckType.PROCESSED
        "FAILED"     | AckType.FAILED
        "RESET_DONE" | AckType.RESET_DONE
        "READY"      | AckType.READY
    }

    def "should handle case-insensitive conversion"() {
        expect:
        AckType.fromString("received") == AckType.RECEIVED
        AckType.fromString("PROCESSED") == AckType.PROCESSED
        AckType.fromString("Failed") == AckType.FAILED
    }

    def "should throw exception for unknown ack type"() {
        when:
        AckType.fromString("UNKNOWN")

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "Unknown ack type: UNKNOWN"
    }

    def "should provide string value"() {
        expect:
        AckType.RECEIVED.value == "RECEIVED"
        AckType.PROCESSED.value == "PROCESSED"
        AckType.FAILED.value == "FAILED"
        AckType.RESET_DONE.value == "RESET_DONE"
        AckType.READY.value == "READY"
    }
}
