package com.ajaxsystems.infrastructure.kafka.service

import com.ajaxsystems.application.useCases.FindTransactionsRestApiInputPort
import com.ajaxsystems.infrastructure.database.mapper.toListResponse
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.data.domain.Pageable
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import proto.ProtoMessage
import reactor.core.publisher.Flux


@Service
class KafkaConsumerService constructor(
    private val consumerTemplate: ReactiveKafkaConsumerTemplate<String, ByteArray>,
    private val kafkaProducerService: KafkaProducerService,
    private val useCase: FindTransactionsRestApiInputPort,
) : CommandLineRunner {

    private val log: Logger = LoggerFactory.getLogger(KafkaConsumerService::class.java)

    @Value(value = "\${kafka.producer.topic}")
    private lateinit var responseTopic: String

    override fun run(vararg args: String) {
        consume().subscribe()
    }

    private fun consume(): Flux<ByteArray> {
        return consumerTemplate
            .receiveAutoAck()
            .doOnNext {
                log.info(
                    "received key={}, value={} from topic={}, offset={}",
                    it.key(),
                    it.value(),
                    it.topic(),
                    it.offset()
                )
            }
            .map { obj: ConsumerRecord<*, *> -> obj.value() as ByteArray }
            .map {
                val message = ProtoMessage.FindTransactionsByPersonIdAndTimeRequest.parseFrom(it)
                log.info("received message:\n{}", message)
                message
            }
            .onErrorContinue { throwable, _ ->
                log.error(
                    "something bad happened while consuming : {}",
                    throwable.message
                )
            }
            .flatMap {
                useCase.findAllByIdAndBetweenDateAndExchangeCurrency(
                    UUID.fromString(it.personId),
                    LocalDateTime.ofEpochSecond(it.startDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(it.endDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    Pageable.ofSize(it.size).withPage(it.page),
                    it.currency
                )
                    .map { it.toListResponse().toByteArray() }
            }
            .doOnNext { kafkaProducerService.send(responseTopic, it) }
    }
}
