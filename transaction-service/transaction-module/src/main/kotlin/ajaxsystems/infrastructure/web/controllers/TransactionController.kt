package ajaxsystems.infrastructure.web.controllers

import ajaxsystems.application.useCases.FindTransactionsRestApiUseCase
import ajaxsystems.domain.dto.TransactionDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import java.time.LocalDateTime
import java.util.UUID
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TransactionController(
    private val useCase: FindTransactionsRestApiUseCase,
) {
    @Operation(summary = "Get all transaction by ID with currency between dates.")
    @ApiResponse(
        responseCode = "200",
        description = "Person is found.",
        content = [Content(
            mediaType = "application/json",
        )]
    )
    @GetMapping(value = ["transactions/{id}/{currency}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getTransactions(
        @PathVariable(name = "id", required = true) id: UUID,
        @PathVariable(name = "currency", required = true) currency: String,
        @RequestParam(name = "startdate", required = true)
        @DateTimeFormat(fallbackPatterns = ["yyyy-MM-dd HH:mm:ss"]) startDate: LocalDateTime,
        @RequestParam(name = "enddate", required = false)
        @DateTimeFormat(fallbackPatterns = ["yyyy-MM-dd HH:mm:ss"]) endDate: LocalDateTime?,
        @ParameterObject @PageableDefault(page = 0, size = 5) pageable: Pageable
    ): ResponseEntity<Mono<List<TransactionDto>>> {
        val transactions =
            useCase.findAllByIdAndBetweenDateAndExchangeCurrency(id, startDate, endDate, pageable, currency)
        return ResponseEntity<Mono<List<TransactionDto>>>(transactions, HttpStatus.OK)
    }
}
