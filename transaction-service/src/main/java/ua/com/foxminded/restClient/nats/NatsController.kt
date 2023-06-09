package ua.com.foxminded.restClient.nats

import com.fasterxml.jackson.databind.json.JsonMapper
import io.nats.client.Dispatcher
import io.nats.client.Message
import io.nats.client.MessageHandler
import io.swagger.v3.core.util.Json
import org.apache.tomcat.util.json.JSONParserTokenManager
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.GsonJsonParser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import java.time.LocalDateTime
import java.util.*
import javax.validation.Payload

@Component
class NatsController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val dispatcher: Dispatcher
) {

    init {
        dispatcher.subscribe("get.transactions") { msg -> handleMessage(msg) }
    }


    fun handleMessage(message: Message) {
        println(message)
        val jsonMap: Map<String, Any?> =
            GsonJsonParser().parseMap(message.data.decodeToString())
        println(jsonMap)
        val id = UUID.fromString(jsonMap.get("id").toString())
        val currency = jsonMap.get("currency")
        val startDate = LocalDateTime.parse("startDate")
        val endDate = LocalDateTime.parse("endDate")
        val page = Integer.parseInt(jsonMap.get("page").toString())
        val size = Integer.parseInt(jsonMap.get("size").toString())

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