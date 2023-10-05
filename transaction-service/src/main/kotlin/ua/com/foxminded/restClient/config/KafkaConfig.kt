package ua.com.foxminded.restClient.config

import java.util.Collections
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.sender.SenderOptions


@Configuration
class KafkaConfig {
    @Bean
    fun kafkaReceiverOptions(
        @Value(value = "\${kafka.consumer.topic}") topic: String, kafkaProperties: KafkaProperties
    ): ReceiverOptions<String, ByteArray> {
        val basicReceiverOptions: ReceiverOptions<String, ByteArray> =
            ReceiverOptions.create(kafkaProperties.buildConsumerProperties())
        return basicReceiverOptions.subscription(Collections.singletonList(topic))
    }

    @Bean
    fun reactiveKafkaConsumerTemplate(
        kafkaReceiverOptions: ReceiverOptions<String, ByteArray>
    ): ReactiveKafkaConsumerTemplate<String, ByteArray> {
        return ReactiveKafkaConsumerTemplate(kafkaReceiverOptions)
    }

    @Bean
    fun reactiveKafkaProducerTemplate(properties: KafkaProperties): ReactiveKafkaProducerTemplate<String, ByteArray> {
        val props = properties.buildProducerProperties()
        return ReactiveKafkaProducerTemplate<String, ByteArray>(SenderOptions.create(props))
    }
}
