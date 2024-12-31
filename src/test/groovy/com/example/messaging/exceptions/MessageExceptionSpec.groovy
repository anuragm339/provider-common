package com.example.messaging.exceptions

import com.example.messaging.exceptions.ConnectionException
import com.example.messaging.exceptions.MessageException
import com.example.messaging.exceptions.ProcessingException
import com.example.messaging.exceptions.ValidationException
import spock.lang.Specification
import spock.lang.Unroll

class MessageExceptionSpec extends Specification {

    def "ConnectionException should be retryable by default"() {
        when:
        def exception = new ConnectionException("Connection lost", MessageException.ErrorCode.CONNECTION_LOST)

        then:
        exception.retryable
        exception.errorCode == MessageException.ErrorCode.CONNECTION_LOST
        exception.message == "Connection lost"
    }

    def "ValidationException should not be retryable"() {
        when:
        def exception = new ValidationException("Invalid message format")

        then:
        !exception.retryable
        exception.errorCode == MessageException.ErrorCode.VALIDATION_ERROR
        exception.message == "Invalid message format"
    }

    @Unroll
    def "ProcessingException should handle retryable flag correctly: #retryable"() {
        when:
        def exception = new ProcessingException(
                "Processing failed",
                MessageException.ErrorCode.PROCESSING_ERROR,
                retryable
        )

        then:
        exception.retryable == retryable
        exception.errorCode == MessageException.ErrorCode.PROCESSING_ERROR

        where:
        retryable << [true, false]
    }

    def "Exceptions should properly chain causes"() {
        given:
        def cause = new RuntimeException("Original error")

        when:
        def exception = new ConnectionException(
                "Connection failed",
                MessageException.ErrorCode.CONNECTION_TIMEOUT,
                cause
        )

        then:
        exception.cause == cause
        exception.retryable
        exception.errorCode == MessageException.ErrorCode.CONNECTION_TIMEOUT
    }
}
