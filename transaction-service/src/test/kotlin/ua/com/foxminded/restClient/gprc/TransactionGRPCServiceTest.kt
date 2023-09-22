package ua.com.foxminded.restClient.gprc

import java.time.LocalDateTime
import java.util.Currency
import java.util.UUID
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import utils.Transactions

class TransactionGRPCServiceTest {

    val transactionService = Mockito.mock(TransactionService::class.java)

    val exchangeService = Mockito.mock(CurrencyExchangeService::class.java)

    val grpcService = TransactionGRPCService(transactionService, exchangeService)

    @Test
    fun `findTransactionsByPersonIdAndTime should return ListResponse when input correct`() {
        val expectedSize = 2

        Mockito.`when`(
            transactionService.findAllByIdAndBetweenDate(
                any<UUID>(),
                any<LocalDateTime>(),
                any<LocalDateTime>(),
                any<Pageable>()
            )
        )
            .thenReturn(Flux.fromIterable(Transactions.transactionList))
        Mockito.`when`(exchangeService.exchangeTo(eq(Transactions.transactionList[0]), any<Currency>()))
            .thenReturn(Transactions.transactionList[0])
        Mockito.`when`(exchangeService.exchangeTo(eq(Transactions.transactionList[1]), any<Currency>()))
            .thenReturn(Transactions.transactionList[1])

        StepVerifier.create(grpcService.findTransactionsByPersonIdAndTime(Mono.just(Transactions.transactionRequest)))
            .expectNextMatches { it.transactionCount == expectedSize }
            .verifyComplete()
    }
}
