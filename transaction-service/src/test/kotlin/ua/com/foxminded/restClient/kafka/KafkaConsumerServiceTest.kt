package ua.com.foxminded.restClient.kafka

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
import ua.com.foxminded.restClient.config.DBTestConfig
import ua.com.foxminded.restClient.service.RateService
import utils.Transactions

@SpringBootTest
@EmbeddedKafka(topics = ["test_transactions", "test_transactions_produce"], partitions = 1)
class KafkaConsumerServiceTest : DBTestConfig() {

    @Value(value = "\${kafka.consumer.topics}")
    private lateinit var consumerTopics: List<String>

    @MockBean
    private lateinit var rateService: RateService

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
            .handle { obj, sink ->
                val byteArray = obj.value() as ByteArray
                when (String(byteArray)) {
                    "Hello" -> {}
                    else -> sink.next(byteArray)
                }
            }
            .doOnNext {
                messageList.add(ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse.parseFrom(it))
                latch.countDown()
            }.subscribe()

        Mockito.`when`(rateService.rates()).thenReturn(Transactions.rates)
        kafkaProducerTemplate.send(consumerTopics[0], request.toByteArray()).subscribe()
        latch.await()

        assertEquals(expectedSize, messageList[0].success.transactionsCount)
        assertEquals(Transactions.transactionRequest.personId, messageList[0].success.getTransactions(0).personId)
    }
}
