package ua.com.foxminded.restClient.gprc

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.com.foxminded.restClient.config.DBTestConfig
import ua.com.foxminded.restClient.service.RateService
import utils.Transactions

@SpringBootTest
@AutoConfigureWebTestClient
class TransactionGRPCServiceTest : DBTestConfig() {

    @MockBean
    private lateinit var rateService: RateService

    @Autowired
    private lateinit var grpcService: TransactionGRPCService

    @Test
    fun `findTransactionsByPersonIdAndTime should return ListResponse when input correct`() {
        val expectedSize = 2

        Mockito.`when`(rateService.rates()).thenReturn(Transactions.rates)

        StepVerifier.create(grpcService.findTransactionsByPersonIdAndTime(Mono.just(Transactions.transactionRequest)))
            .expectNextMatches { it.hasSuccess() }
            .expectNextMatches { it.success.transactionsCount == expectedSize }
            .verifyComplete()
    }
}
