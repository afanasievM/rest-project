package ua.com.foxminded.restClient.kafka

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService constructor(
    private val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, ByteArray>
) {
    fun send(topic: String, byteArray: ByteArray) {
        reactiveKafkaProducerTemplate.send(topic, byteArray).subscribe()
    }
}
