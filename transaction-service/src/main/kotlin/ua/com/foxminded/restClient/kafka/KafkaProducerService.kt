package ua.com.foxminded.restClient.kafka

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService constructor(
    private val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, ByteArray>
) {

    @Value(value = "\${kafka.producer.topic}")
    private lateinit var topic: String

    fun send(byteArray: ByteArray) {
        reactiveKafkaProducerTemplate.send(topic, byteArray).subscribe()
    }
}
