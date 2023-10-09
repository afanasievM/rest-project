package ua.com.foxminded.restClient.kafka

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
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
import reactor.core.publisher.Mono
import ua.com.foxminded.restClient.mapper.toListResponse
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService


@Service
class KafkaConsumerService constructor(
    private val consumerTemplate: ReactiveKafkaConsumerTemplate<String, ByteArray>,
    private val kafkaProducerService: KafkaProducerService,
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
) : CommandLineRunner {

    private val log: Logger = LoggerFactory.getLogger(KafkaConsumerService::class.java)

    @Value(value = "\${kafka.producer.topic}")
    private lateinit var responseTopic: String

    override fun run(vararg args: String) {
        consume().subscribe()
    }

    private fun consume(): Mono<ByteArray> {
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
                transactionService.findAllByIdAndBetweenDate(
                    UUID.fromString(it.personId),
                    LocalDateTime.ofEpochSecond(it.startDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(it.endDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    Pageable.ofSize(it.size).withPage(it.page)
                )
            }
            .map { exchangeService.exchangeTo(it, Currency.getInstance(it.currency)) }
            .collectList()
            .map { it.toListResponse().toByteArray() }
            .doOnNext {
                kafkaProducerService.send(responseTopic, it)
            }
    }
}
