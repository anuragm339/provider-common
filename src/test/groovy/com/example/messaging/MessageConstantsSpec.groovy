package com.example.messaging

import com.example.messaging.constants.MessageConstants
import spock.lang.Specification

class MessageConstantsSpec extends Specification {

    def "batch constants should have valid values"() {
        expect:
        MessageConstants.MAX_BATCH_RETRIES > 0
        MessageConstants.MAX_BATCH_SIZE > MessageConstants.MIN_BATCH_SIZE
        MessageConstants.BATCH_TIMEOUT_MS > 0
    }

    def "retry constants should have sensible values"() {
        expect:
        MessageConstants.RETRY_MAX_DELAY_MS > MessageConstants.RETRY_INITIAL_DELAY_MS
        MessageConstants.MAX_RETRY_ATTEMPTS > 0
    }

    def "message constants should have reasonable limits"() {
        expect:
        MessageConstants.MAX_MESSAGE_SIZE_BYTES > 0
        MessageConstants.MESSAGE_TTL_MS > 0
    }

    def "should not be able to instantiate MessageConstants"() {
        when:
        new MessageConstants()

        then:
        thrown(UnsupportedOperationException)
    }
}
