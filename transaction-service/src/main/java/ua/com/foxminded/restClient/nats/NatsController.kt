package ua.com.foxminded.restClient.nats

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.nats.client.Connection
import io.nats.client.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.connection.ReactiveSubscription
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.dto.TransactionNatsDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Component
class NatsController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val natsConnection: Connection,
    private val mapper: ObjectMapper
) {
    private val identificator = "transactions.service"

    init {
        val dispatcher = natsConnection.createDispatcher()
        natsConnection
        dispatcher.subscribe("hello") { message ->
            println(message.replyTo)
            val msg = Flux
                .interval(Duration.ofSeconds(1))
                .take(10)

                .subscribe {
                    println(it)
                    natsConnection.publish(message.replyTo, it.toString().toByteArray())
                }

        }

        dispatcher.subscribe("get.transactions") {
            if (it.replyTo != identificator)
                handleMessage(it)
        }

    }


    fun handleMessage(message: Message) {
        println(message.data.decodeToString())
        val request = mapper.readValue<TransactionNatsDto>(message.data.decodeToString())
        val transactions = findTransactions(
            request.id!!, request.startDate!!, request.endDate,
            Pageable.ofSize(request.size!!).withPage(request.page!!)
        )
        transactions
            .map { exchangeService.exchangeTo(it, Currency.getInstance(request.currency)) }
            .subscribe {
                natsConnection.publish(message.replyTo, identificator, it.toString().toByteArray())
            }

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
