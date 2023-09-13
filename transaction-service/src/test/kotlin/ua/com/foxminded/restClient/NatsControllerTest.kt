package ua.com.foxminded.restClient

import com.google.protobuf.Timestamp
import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.Nats
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Currency
import java.util.UUID
import java.util.concurrent.CountDownLatch
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.data.domain.Pageable
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.enums.Direction
import ua.com.foxminded.restClient.nats.NatsController
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NatsControllerTest {

    val testDtos: List<TransactionDto> = getListTransactions()

    val transactionService = Mockito.mock(TransactionService::class.java)

    val exchangeService = Mockito.mock(CurrencyExchangeService::class.java)

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
        natsController = NatsController(transactionService, exchangeService, natsConnection)
    }

    @Test
    fun `nats should return flux transactions when input correct`(){
        val request: ProtoMessage.TransactionRequestProto = getTransactionRequest()
        val latch = CountDownLatch(2)
        val responseMessages: MutableList<Message> = mutableListOf()
        val expectedSize = 2
        val dispatcher = natsConnection.createDispatcher {
            responseMessages.add(it)
            latch.countDown()
        }
        dispatcher.subscribe(NATS_RESPONSE_TOPIC)

        Mockito.`when`(
            transactionService.findAllByIdAndBetweenDate(
                any<UUID>(),
                any<LocalDateTime>(),
                any<LocalDateTime>(),
                any<Pageable>()
            )
        )
            .thenReturn(Flux.fromIterable(testDtos))
        Mockito.`when`(exchangeService.exchangeTo(eq(testDtos[0]), any<Currency>()))
            .thenReturn(testDtos[0])
        Mockito.`when`(exchangeService.exchangeTo(eq(testDtos[1]), any<Currency>()))
            .thenReturn(testDtos[1])
        natsConnection.publish(NATS_REQUEST_TOPIC, NATS_RESPONSE_TOPIC, request.toByteArray())
        latch.await()
        val responseIds = responseMessages.map { ProtoMessage.TransactionResponseProto.parseFrom(it.data).id }

        assertEquals(expectedSize, responseMessages.size)
        assertEquals(responseIds.contains(testDtos[0].id.toString()), true)
    }

    @AfterAll
    fun shutDown() {
        natsConnection.close()
        nats.stop()
    }

    private fun getTransactionRequest(): ProtoMessage.TransactionRequestProto {
        val startDate = LocalDateTime.parse("2022-11-01T12:00:00").atZone(ZoneId.systemDefault()).toInstant()
        val endDate = LocalDateTime.parse("2022-11-01T12:00:00").atZone(ZoneId.systemDefault()).toInstant()
        return ProtoMessage.TransactionRequestProto.newBuilder().apply {
            personId = "e966f608-4621-11ed-b878-0242ac120002"
            currency = "UAH"
            startDateBuilder.setSeconds(startDate.epochSecond).setNanos(startDate.nano)   
            endDateBuilder.setSeconds(endDate.epochSecond).setNanos(endDate.nano)
            page = 0
            size = 2
         }.build()
    }

    private fun getListTransactions(): List<TransactionDto> {
        val dto1 = TransactionDto(
            id = UUID.fromString("e966f601-4621-11ed-b878-0242ac120002"),
            personId = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002"),
            transactionTime = LocalDateTime.parse("2022-12-01T12:00"),
            transactionDirection = Direction.OUTPUT,
            value = 36650.001525878906,
            currency = "UAH",
            iban = "GB29NWBK60161331926819"
        )
        val dto2 = TransactionDto(
            id = UUID.fromString("e966f602-4621-11ed-b878-0242ac120002"),
            personId = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002"),
            transactionTime = LocalDateTime.parse("2022-12-02T12:00"),
            transactionDirection = Direction.OUTPUT,
            value = 36650.001525878906,
            currency = "UAH",
            iban = "GB29NWBK60161331926819"
        )
        return listOf(dto1, dto2)
    }

    companion object {
        const val NATS_PORT = 4222
        const val NATS_MGMT_PORT = 8222
        const val NATS_REQUEST_TOPIC = "get.transactions"
        const val NATS_RESPONSE_TOPIC = "test"
    }
}
