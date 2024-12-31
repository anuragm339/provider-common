package com.example.messaging.models

import com.example.messaging.models.AckType
import com.example.messaging.models.BatchAck
import com.example.messaging.models.BatchState
import com.example.messaging.models.MessageAck
import spock.lang.Specification
import spock.lang.Unroll
import java.time.Instant

class BatchAckSpec extends Specification {

    def "should correctly determine batch state based on acks"() {
        given:
        def acks = createAcks(totalSize, failedCount)

        when:
        def batchAck = BatchAck.builder()
                .batchId("batch-1")
                .consumerId("consumer-1")
                .acks(acks)
                .build()

        then:
        batchAck.batchState == expectedState

        where:
        totalSize | failedCount | expectedState
        5         | 0           | BatchState.COMPLETE
        3         | 1           | BatchState.PARTIAL_FAILED
        2         | 0           | BatchState.PARTIAL
    }

    def "should track failed sequences correctly"() {
        given:
        def acks = [
                createAck(0, AckType.PROCESSED),
                createAck(1, AckType.FAILED),
                createAck(2, AckType.PROCESSED),
                createAck(3, AckType.FAILED)
        ]

        when:
        def batchAck = BatchAck.builder()
                .batchId("batch-1")
                .consumerId("consumer-1")
                .acks(acks)
                .build()

        then:
        batchAck.failedSequences == [1, 3]
        batchAck.batchState == BatchState.PARTIAL_FAILED
    }

    def "should calculate missing sequences"() {
        given:
        def acks = [
                createAck(0, AckType.PROCESSED),
                createAck(2, AckType.PROCESSED),
                createAck(4, AckType.PROCESSED)
        ]

        when:
        def batchAck = BatchAck.builder()
                .batchId("batch-1")
                .consumerId("consumer-1")
                .acks(acks)
                .build()

        then:
        batchAck.getMissingSequences() == [1, 3] as Set
    }

    private MessageAck createAck(int sequence, AckType ackType) {
        return MessageAck.builder()
                .msgOffset(sequence)
                .consumerId("consumer-1")
                .ackType(ackType)
                .sequenceInBatch(sequence)
                .batchSize(5)
                .build()
    }

    private List<MessageAck> createAcks(int total, int failed) {
        def acks = []
        for (i in 0..<total) {
            acks << createAck(i, i < failed ? AckType.FAILED : AckType.PROCESSED)
        }
        return acks
    }
}
