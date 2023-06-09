package ua.com.foxminded.restClient.nats

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import java.time.LocalDateTime
import java.util.*

@Component
class NatsController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val natsConnection: Connection
) {
    private val identificator = "transactions"

    init {
        val dispatcher = natsConnection.createDispatcher()
        dispatcher.subscribe("get.transactions") {
//            if (it.replyTo != identificator)
                handleMessage(it)
        }
    }


    fun handleMessage(message: Message) {
        println(message.data.decodeToString())
        val mapper = jacksonObjectMapper()
        val jsonMap = mapper.readValue<Map<String, String>>(message.data.decodeToString())
        val id = UUID.fromString(jsonMap["id"])
        val currency = jsonMap["currency"]
        val startDate = LocalDateTime.parse(jsonMap["startDate"])
        val endDate = LocalDateTime.parse(jsonMap["endDate"])
        val page = Integer.parseInt(jsonMap["page"])
        val size = Integer.parseInt(jsonMap["size"])
        natsConnection.publish(message.replyTo, message.subject, "dsadad".toByteArray())
        println(findTransactions(id, startDate, endDate, Pageable.ofSize(size).withPage(page)))
    }

    private fun findTransactions(
        id: UUID, startDate: LocalDateTime, endDate: LocalDateTime?, pageable: Pageable
    ): Page<TransactionDto> {
        val transactions = if (endDate != null && endDate.isAfter(startDate)) {
            transactionService.findAllByIdAndBetweenDate(id, startDate, endDate, pageable)
        } else {
            transactionService.findAllByIdAndBetweenDate(id, startDate, LocalDateTime.now(), pageable)
        }
        return transactions
    }


}
