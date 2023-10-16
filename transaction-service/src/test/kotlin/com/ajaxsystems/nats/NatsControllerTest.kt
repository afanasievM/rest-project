package com.ajaxsystems.nats

import com.ajaxsystems.application.useCases.FindTransactionsInputPort
import com.ajaxsystems.domain.dto.TransactionDto
import com.ajaxsystems.infrastructure.nats.controller.NatsController
import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.Nats
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.CountDownLatch
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.data.domain.Pageable
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import proto.ProtoMessage
import reactor.core.publisher.Mono
import utils.Transactions

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NatsControllerTest {

    val testDtos: List<TransactionDto> = Transactions.transactionList

    val useCase = Mockito.mock(FindTransactionsInputPort::class.java)

    lateinit var natsUrl: String

    lateinit var natsConnection: Connection

    lateinit var natsController: NatsController

    lateinit var nats: GenericContainer<*>

    @BeforeAll
    fun setUp() {
        nats = GenericContainer("nats:latest").withExposedPorts(NATS_PORT, NATS_MGMT_PORT)
        nats.start()
        natsUrl = "nats://%s:%d".format(nats.host, nats.getMappedPort(NATS_PORT))
        natsConnection = Nats.connect(natsUrl)
        natsController = NatsController(useCase, natsConnection)
    }

    @Test
    fun `nats should return flux transactions when input correct`() {
        val request: ProtoMessage.FindTransactionsByPersonIdAndTimeRequest = Transactions.transactionRequest
        val latch = CountDownLatch(1)
        val responseMessages: MutableList<Message> = mutableListOf()
        val expectedSize = 2
        val dispatcher = natsConnection.createDispatcher {
            responseMessages.add(it)
            latch.countDown()
        }
        dispatcher.subscribe(NATS_RESPONSE_TOPIC)

        Mockito.`when`(
            useCase.findAllByIdAndBetweenDateAndExchangeCurrency(
                any<UUID>(),
                any<LocalDateTime>(),
                any<LocalDateTime>(),
                any<Pageable>(),
                any<String>()
            )
        )
            .thenReturn(Mono.just(testDtos))
        natsConnection.publish(NATS_REQUEST_TOPIC, NATS_RESPONSE_TOPIC, request.toByteArray())
        latch.await()
        val responseList =  ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse
            .parseFrom(responseMessages[0].data)
            .success
            .transactionsList


        assertEquals(expectedSize, responseList.size)
        assertEquals(testDtos[0].id.toString() == responseList[0].id, true)
    }

    @AfterAll
    fun shutDown() {
        natsConnection.close()
        nats.stop()
    }

    companion object {
        const val NATS_PORT = 4222
        const val NATS_MGMT_PORT = 8222
        const val NATS_REQUEST_TOPIC = "get.transactions"
        const val NATS_RESPONSE_TOPIC = "test"
    }
}
