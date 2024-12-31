package com.example.messaging.models

import com.example.messaging.models.request.*
import com.example.messaging.models.response.*
import com.example.messaging.models.enums.*
import spock.lang.Specification
import spock.lang.Unroll

class RequestResponseSpec extends Specification {

    @Unroll
    def "should create ResetRequest with type #resetType"() {
        when:
        def request = ResetRequest.builder()
                .requestId("req-1")
                .consumerId("consumer-1")
                .resetType(resetType)
                .build()

        then:
        request.resetType == resetType
        request.timestamp != null
        request.requestId == "req-1"
        request.consumerId == "consumer-1"

        where:
        resetType << [ResetType.FULL, ResetType.PARTIAL]
    }

    @Unroll
    def "should create StatusResponse with status #status"() {
        when:
        def response = StatusResponse.builder()
                .requestId("req-1")
                .consumerId("consumer-1")
                .status(status)
                .currentOffset(1000L)
                .message(message)
                .build()

        then:
        response.status == status
        response.message == message
        response.timestamp != null
        response.requestId == "req-1"
        response.consumerId == "consumer-1"
        response.currentOffset == 1000L

        where:
        status                  | message
        ResponseStatus.OK       | "Success"
        ResponseStatus.ERROR    | "Failed"
        ResponseStatus.IN_PROGRESS | "Processing"
        ResponseStatus.RESETTING   | "Reset in progress"
    }

    def "ResetType enum should handle string conversion correctly"() {
        expect: "correct string to enum conversion"
        ResetType.fromString("FULL") == ResetType.FULL
        ResetType.fromString("PARTIAL") == ResetType.PARTIAL
        ResetType.fromString("full") == ResetType.FULL  // case insensitive

        when: "invalid string is provided"
        ResetType.fromString("INVALID")

        then: "should throw exception"
        def e = thrown(IllegalArgumentException)
        e.message == "Unknown reset type: INVALID"
    }

    def "ResponseStatus enum should handle string conversion correctly"() {
        expect: "correct string to enum conversion"
        ResponseStatus.fromString("OK") == ResponseStatus.OK
        ResponseStatus.fromString("ERROR") == ResponseStatus.ERROR
        ResponseStatus.fromString("IN_PROGRESS") == ResponseStatus.IN_PROGRESS
        ResponseStatus.fromString("RESETTING") == ResponseStatus.RESETTING
        ResponseStatus.fromString("ok") == ResponseStatus.OK  // case insensitive

        when: "invalid string is provided"
        ResponseStatus.fromString("INVALID")

        then: "should throw exception"
        def e = thrown(IllegalArgumentException)
        e.message == "Unknown response status: INVALID"
    }
}
