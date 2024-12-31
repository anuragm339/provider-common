package com.example.messaging.models

import com.example.messaging.models.AckType
import com.example.messaging.models.BatchAck
import com.example.messaging.models.MessageAck
import spock.lang.Specification
import spock.lang.Unroll

class AcknowledgmentSpec extends Specification {

    def "should create MessageAck with all fields"() {
        when:
        def ack = MessageAck.builder()
                .msgOffset(1000L)
                .consumerId("consumer-1")
                .ackType(AckType.PROCESSED)
                .processingTimeMs(150L)
                .build()

        then:
        ack.msgOffset == 1000L
        ack.consumerId == "consumer-1"
        ack.ackType == MessageAck.AckType.PROCESSED
        ack.processingTimeMs == 150L
        ack.ackTime != null
    }

    @Unroll
    def "should handle different ack types: #ackType"() {
        when:
        def ack = MessageAck.builder()
                .msgOffset(1L)
                .consumerId("consumer-1")
                .ackType(ackType)
                .build()

        then:
        ack.ackType == ackType

        where:
        ackType << AckType.values()
    }

    def "should create BatchAck with multiple message acks"() {
        given:
        def acks = [
                MessageAck.builder()
                        .msgOffset(1L)
                        .consumerId("consumer-1")
                        .ackType(AckType.PROCESSED)
                        .build(),
                MessageAck.builder()
                        .msgOffset(2L)
                        .consumerId("consumer-1")
                        .ackType(MessageAck.AckType.PROCESSED)
                        .build()
        ]

        when:
        def batchAck = BatchAck.builder()
                .consumerId("consumer-1")
                .acks(acks)
                .batchSequence(1L)
                .build()

        then:
        batchAck.consumerId == "consumer-1"
        batchAck.acks.size() == 2
        batchAck.batchSequence == 1L
        batchAck.ackTime != null
    }

    def "BatchAck should create immutable copy of acks list"() {
        given:
        def originalAcks = [
                MessageAck.builder()
                        .msgOffset(1L)
                        .consumerId("consumer-1")
                        .ackType(MessageAck.AckType.PROCESSED)
                        .build()
        ]

        when:
        def batchAck = BatchAck.builder()
                .consumerId("consumer-1")
                .acks(originalAcks)
                .batchSequence(1L)
                .build()

        then:
        thrown(UnsupportedOperationException) {
            batchAck.acks.add(
                    MessageAck.builder()
                            .msgOffset(2L)
                            .consumerId("consumer-1")
                            .ackType(MessageAck.AckType.PROCESSED)
                            .build()
            )
        }
    }
}
