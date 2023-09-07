package ua.com.foxminded.restClient.nats

import com.google.protobuf.Timestamp
import io.nats.client.Connection
import io.nats.client.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
import java.util.UUID

@Component
class NatsController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val natsConnection: Connection
) {
    private val identificator = "transactions.service"

    init {
        val dispatcher = natsConnection.createDispatcher()
        dispatcher.subscribe("get.transactions") {
            if (it.replyTo != identificator)
                handleMessage(it)
        }
    }


    fun handleMessage(message: Message) {
        val request = ProtoMessage.TransactionRequestProto.parseFrom(message.data)
        findTransactions(
            UUID.fromString(request.personId),
            LocalDateTime.ofEpochSecond(request.startDate.seconds, request.endDate.nanos, ZoneOffset.UTC),
            LocalDateTime.ofEpochSecond(request.endDate.seconds, request.endDate.nanos, ZoneOffset.UTC),
            Pageable.ofSize(request.size).withPage(request.page)
        )
            .map { exchangeService.exchangeTo(it, Currency.getInstance(request.currency)) }
            .map { mapTransactionDtoToResponse(it) }
            .subscribe {
                natsConnection.publish(message.replyTo, identificator, it.toByteArray())
            }

    }

    private fun mapTransactionDtoToResponse(dto: TransactionDto): ProtoMessage.TransactionResponseProto {
        return ProtoMessage.TransactionResponseProto.newBuilder()
            .setId(dto.id.toString())
            .setPersonId(dto.personId.toString())
            .setTransactionTime(
                Timestamp.newBuilder()
                    .setSeconds(dto.transactionTime?.toEpochSecond(ZoneOffset.UTC) ?: 0)
                    .setNanos(dto.transactionTime?.nano ?: 0)
            )
            .setValue(dto.value ?: 0.0)
            .setCurrency(dto.currency)
            .setIban(dto.iban)
            .build()
    }

    private fun findTransactions(
        id: UUID, startDate: LocalDateTime, endDate: LocalDateTime?, pageable: Pageable
    ): Flux<TransactionDto> {
        val transactions = if (endDate != null && endDate.isAfter(startDate)) {
            transactionService.findAllByIdAndBetweenDate(id, startDate, endDate, pageable)
        } else {
            transactionService.findAllByIdAndBetweenDate(id, startDate, LocalDateTime.now(), pageable)
        }
        return transactions
    }


}
