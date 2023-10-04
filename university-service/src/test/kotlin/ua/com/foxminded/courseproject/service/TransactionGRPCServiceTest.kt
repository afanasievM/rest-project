package ua.com.foxminded.courseproject.service

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.data.domain.Pageable
import proto.ProtoMessage
import proto.ProtoMessage.FindTransactionsByPersonIdAndTimeResponse
import proto.ReactorTransactionsServiceGrpc
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.dto.TransactionDto
import ua.com.foxminded.courseproject.enums.Direction

class TransactionGRPCServiceTest {

    private val grpcStub = Mockito.mock(ReactorTransactionsServiceGrpc.ReactorTransactionsServiceStub::class.java)

    private val transactionService = TransactionGRPCServiceImpl(grpcStub)

    @Test
    fun `get transactions should return list of transactionDto`() {
        val id = "e966f608-4621-11ed-b878-0242ac120002"
        val startDate = LocalDateTime.parse("2022-11-01T12:00:00")
        val endDate = LocalDateTime.parse("2022-12-02T12:00:00")
        val currency = "UAH"
        val pageable = Pageable.ofSize(1).withPage(0)
        val expected: List<TransactionDto> = getListTransactions()

        Mockito.`when`(
            grpcStub.findTransactionsByPersonIdAndTime(
                any<Mono<ProtoMessage.FindTransactionsByPersonIdAndTimeRequest>>()
            )
        ).thenReturn(Mono.just(buildTransactionListResponse()))

        StepVerifier.create(transactionService.getTransactions(id, currency, startDate, endDate, pageable))
            .expectNextMatches { expected == it }
            .verifyComplete()
    }


    private fun buildTransactionListResponse(): ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse {
        val responseTime1 = LocalDateTime.parse("2022-12-01T12:00").toInstant(ZoneOffset.UTC)
        val response1 = FindTransactionsByPersonIdAndTimeResponse.newBuilder().apply {
            id = "e966f601-4621-11ed-b878-0242ac120002"
            personId = "e966f608-4621-11ed-b878-0242ac120002"
            transactionTimeBuilder.setSeconds(responseTime1.epochSecond).setNanos(responseTime1.nano)
            direction = FindTransactionsByPersonIdAndTimeResponse.Direction.OUTPUT
            value = 36650.001525878906
            currency = "UAH"
            iban = "GB29NWBK60161331926819"
        }.build()
        val responseTime2 = LocalDateTime.parse("2022-12-02T12:00").toInstant(ZoneOffset.UTC)
        val response2 = FindTransactionsByPersonIdAndTimeResponse.newBuilder().apply {
            id = "e966f602-4621-11ed-b878-0242ac120002"
            personId = "e966f608-4621-11ed-b878-0242ac120002"
            transactionTimeBuilder.setSeconds(responseTime2.epochSecond).setNanos(responseTime2.nano)
            direction = FindTransactionsByPersonIdAndTimeResponse.Direction.OUTPUT
            value = 36650.001525878906
            currency = "UAH"
            iban = "GB29NWBK60161331926819"
        }.build()
        return ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse
            .newBuilder()
            .addTransaction(response1)
            .addTransaction(response2)
            .build()
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
}
