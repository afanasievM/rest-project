package ajaxsystems.infrastructure.nats.controller

import ajaxsystems.application.useCases.FindTransactionsRestApiInputPort
import ajaxsystems.infrastructure.mapper.toListResponse
import io.nats.client.Connection
import io.nats.client.Message
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import proto.ProtoMessage

@Component
class NatsController(
    private val useCase: FindTransactionsRestApiInputPort,
    private val natsConnection: Connection,
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
        useCase.findAllByIdAndBetweenDateAndExchangeCurrency(
            UUID.fromString(request.personId),
            LocalDateTime.ofEpochSecond(request.startDate.seconds, request.endDate.nanos, ZoneOffset.UTC),
            LocalDateTime.ofEpochSecond(request.endDate.seconds, request.endDate.nanos, ZoneOffset.UTC),
            Pageable.ofSize(request.size).withPage(request.page),
            request.currency
        )
            .map { it.toListResponse() }
            .subscribe {
                natsConnection.publish(message.replyTo, identificator, it.toByteArray())
            }
    }
}
