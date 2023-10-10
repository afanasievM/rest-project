package ua.com.foxminded.restClient.kafka

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import proto.ProtoMessage
import utils.Transactions

@SpringBootTest
@AutoConfigureWebTestClient
class KafkaConsumerServiceTest {


    @Autowired
    private lateinit var kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, ByteArray>

    //IT isn't finished test. Don't review
    @Test
    fun `findTransactionsByPersonIdAndTime should return ListResponse when input correct`() {
        val request: ProtoMessage.FindTransactionsByPersonIdAndTimeRequest = Transactions.transactionRequest
        kafkaProducerTemplate.send("transactions",request.toByteArray()).subscribe()
    }
}
