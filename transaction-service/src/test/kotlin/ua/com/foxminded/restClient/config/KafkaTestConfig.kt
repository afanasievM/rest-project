package ua.com.foxminded.restClient.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions


@Configuration
class KafkaTestConfig {

    @Bean
    fun kafkaTestReceiverOptions(
        @Value(value = "\${kafka.producer.topic}") topic:String, kafkaProperties: KafkaProperties
    ): ReceiverOptions<String, ByteArray> {
        val basicReceiverOptions: ReceiverOptions<String, ByteArray> =
            ReceiverOptions.create(kafkaProperties.buildConsumerProperties())
        return basicReceiverOptions
            .consumerProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
            .subscription(listOf(topic))
    }

    @Bean("reactiveKafkaTestConsumerTemplate")
    fun reactiveKafkaTestConsumerTemplate(
        kafkaTestReceiverOptions: ReceiverOptions<String, ByteArray>
    ): ReactiveKafkaConsumerTemplate<String, ByteArray> {
        return ReactiveKafkaConsumerTemplate(kafkaTestReceiverOptions)
    }

}
