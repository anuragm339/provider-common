package com.example.messaging.exceptions

import com.example.messaging.exceptions.AuthenticationException
import com.example.messaging.exceptions.ErrorCode
import com.example.messaging.exceptions.InvalidConsumerStateException
import com.example.messaging.exceptions.UnauthorizedConsumerException
import spock.lang.Specification

class SecurityExceptionSpec extends Specification {

    def "UnauthorizedConsumerException should contain consumer ID and not be retryable"() {
        given:
        def consumerId = "unknown-consumer-123"

        when:
        def exception = new UnauthorizedConsumerException(consumerId)

        then:
        !exception.retryable
        exception.errorCode == ErrorCode.UNAUTHORIZED_CONSUMER
        exception.message.contains(consumerId)
    }

    def "AuthenticationException should not be retryable"() {
        given:
        def message = "Invalid credentials provided"

        when:
        def exception = new AuthenticationException(message)

        then:
        !exception.retryable
        exception.errorCode == ErrorCode.AUTHENTICATION_FAILED
        exception.message == message
    }

    def "InvalidConsumerStateException should include state and action details"() {
        given:
        def consumerId = "consumer-123"
        def currentState = "DISCONNECTED"
        def attemptedAction = "SEND_MESSAGE"

        when:
        def exception = new InvalidConsumerStateException(consumerId, currentState, attemptedAction)

        then:
        !exception.retryable
        exception.errorCode == ErrorCode.INVALID_CONSUMER_STATE
        exception.message.contains(consumerId)
        exception.message.contains(currentState)
        exception.message.contains(attemptedAction)
    }
}
