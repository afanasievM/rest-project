package com.ajaxsystems.kafka

import com.ajaxsystems.application.service.GetRatesUseCase
import java.util.concurrent.CountDownLatch
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import proto.ProtoMessage
import com.ajaxsystems.config.DBTestConfig
import utils.Transactions

@SpringBootTest
@EmbeddedKafka(topics = ["test_transactions", "test_transactions_produce"], partitions = 1)
class KafkaConsumerServiceTest : DBTestConfig() {

    @Value(value = "\${kafka.consumer.topics}")
    private lateinit var consumerTopics: List<String>

    @MockBean
    private lateinit var rateService: GetRatesUseCase

    @Autowired
    private lateinit var kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, ByteArray>

    @Autowired
    @Qualifier("reactiveKafkaTestConsumerTemplate")
    private lateinit var kafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, ByteArray>

    @Test
    fun `findTransactionsByPersonIdAndTime should return ListResponse when input correct`() {
        val request: ProtoMessage.FindTransactionsByPersonIdAndTimeRequest = Transactions.transactionRequest
        val messageList: MutableList<ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse> = mutableListOf()
        val latch = CountDownLatch(1)
        val expectedSize = 2
        kafkaConsumerTemplate.receiveAutoAck()
            .map { it.value() as ByteArray }
            .doOnNext {
                messageList.add(ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse.parseFrom(it))
                latch.countDown()
            }.subscribe()

        Mockito.`when`(rateService.getRates()).thenReturn(Transactions.rates)
        kafkaProducerTemplate.send(consumerTopics[0], request.toByteArray()).subscribe()
        latch.await()

        assertEquals(expectedSize, messageList[0].success.transactionsCount)
        assertEquals(Transactions.transactionRequest.personId, messageList[0].success.getTransactions(0).personId)
    }
}
