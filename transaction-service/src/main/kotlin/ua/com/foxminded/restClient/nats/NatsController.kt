package ua.com.foxminded.restClient.nats

import com.google.protobuf.Timestamp
import io.nats.client.Connection
import io.nats.client.Message
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import protobuf.ProtoMessage
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService

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
        val request = ProtoMessage.FindTransactionsByPersonIdAndTimeRequest.parseFrom(message.data)
        transactionService.findAllByIdAndBetweenDate(
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

    private fun mapTransactionDtoToResponse(
        dto: TransactionDto
    ): ProtoMessage.FindTransactionsByPersonIdAndTimeResponse {
        return ProtoMessage.FindTransactionsByPersonIdAndTimeResponse.newBuilder()
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

}
