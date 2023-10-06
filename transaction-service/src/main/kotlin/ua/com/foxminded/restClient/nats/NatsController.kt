package ua.com.foxminded.restClient.nats

import io.nats.client.Connection
import io.nats.client.Message
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import proto.ProtoMessage
import ua.com.foxminded.restClient.mapper.TransactionMapper
import ua.com.foxminded.restClient.mapper.dtoToProtoResponse
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService

@Component
class NatsController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val natsConnection: Connection,
    private val transactionMapper: TransactionMapper
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
            .map { transactionMapper.dtoToProtoResponse(it) }
            .subscribe {
                natsConnection.publish(message.replyTo, identificator, it.toByteArray())
            }

    }



}
