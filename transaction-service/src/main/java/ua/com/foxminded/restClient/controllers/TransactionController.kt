package ua.com.foxminded.restClient.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.ServerSentEvent
import org.springframework.http.codec.ServerSentEventHttpMessageWriter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService
import ua.com.foxminded.restClient.utils.PageableTransaction
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@RestController
class TransactionController @Autowired constructor(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService
) {
    @Operation(summary = "Get all transaction by ID with currency between dates.")
    @ApiResponse(
        responseCode = "200",
        description = "Person is found.",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = PageableTransaction::class)
        )]
    )
    @GetMapping(value = ["transactions/{id}/{currency}"])
    fun getTransactions(
        @PathVariable(name = "id", required = true) id: UUID,
        @PathVariable(name = "currency", required = true) currency: String,
        @RequestParam(name = "startdate", required = true)
        @DateTimeFormat(fallbackPatterns = ["yyyy-MM-dd HH:mm:ss"]) startDate: LocalDateTime,
        @RequestParam(name = "enddate", required = false)
        @DateTimeFormat(fallbackPatterns = ["yyyy-MM-dd HH:mm:ss"]) endDate: LocalDateTime?,
        @ParameterObject @PageableDefault(page = 0, size = 5) pageable: Pageable
    ): ResponseEntity<Any> {
        val transactions = findTransactions(id, startDate, endDate, pageable)
        return ResponseEntity<Any>(
            exchangeService.exchangeTo(transactions, Currency.getInstance(currency)),
            HttpStatus.OK
        )
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


    @GetMapping(value = ["transactions/gt"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun get(): Mono<String> {
        return Flux.interval(Duration.ofMillis(100)).take(1)
            .onBackpressureBuffer()
            .map { it.toString() }
            .toMono()
    }
}
