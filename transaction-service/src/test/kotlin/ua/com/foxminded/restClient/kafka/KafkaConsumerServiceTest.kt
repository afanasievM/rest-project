package ua.com.foxminded.restClient.kafka

import java.time.Duration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import proto.ProtoMessage
import ua.com.foxminded.restClient.config.DBTestConfig
import ua.com.foxminded.restClient.service.RateService
import utils.Transactions

@SpringBootTest
@EmbeddedKafka(
    topics = [KafkaConsumerServiceTest.CONSUMER_TOPIC, KafkaConsumerServiceTest.PRODUCER_TOPIC],
    partitions = 1
)
@ExtendWith(OutputCaptureExtension::class)
class KafkaConsumerServiceTest : DBTestConfig() {

    @MockBean
    private lateinit var rateService: RateService

    @Autowired
    private lateinit var kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, ByteArray>

    @Autowired
    private lateinit var kafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, ByteArray>

    @Autowired
    private lateinit var kafkaConsumerService: KafkaConsumerService

    //IT isn't finished test. Don't review
    @Test
    fun `findTransactionsByPersonIdAndTime should return ListResponse when input correct`(output: CapturedOutput) {
        val request: ProtoMessage.FindTransactionsByPersonIdAndTimeRequest = Transactions.transactionRequest

        Mockito.`when`(rateService.rates()).thenReturn(Transactions.rates)
        println("try to send")
//        kafkaProducerTemplate.send(CONSUMER_TOPIC, request.toByteArray()).subscribe()
        println("sent")
        kafkaConsumerTemplate.listTopics().collectList().block().forEach { println(it) }

        await().during(Duration.ofMillis(3000))
        println("OUTPUT")
        println(output.all)
        println(output.out)
    }

    companion object{
        const val CONSUMER_TOPIC = "transactions_test"
        const val PRODUCER_TOPIC = "transactions_produce_test"
    }
}
