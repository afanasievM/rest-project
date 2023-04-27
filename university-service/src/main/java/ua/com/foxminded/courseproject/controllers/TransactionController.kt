package ua.com.foxminded.courseproject.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ua.com.foxminded.courseproject.service.TransactionService
import ua.com.foxminded.courseproject.utils.PageableTransaction
import java.time.LocalDateTime

@RestController
class TransactionController @Autowired constructor(private val transactionService: TransactionService) {

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
        @PathVariable(name = "id", required = true) id: String,
        @PathVariable(name = "currency", required = true) currency: String,
        @RequestParam(name = "startdate", required = true)
        @DateTimeFormat(fallbackPatterns = [DATE_TIME_FORMAT]) startDate: LocalDateTime,
        @RequestParam(name = "enddate", required = false)
        @DateTimeFormat(fallbackPatterns = [DATE_TIME_FORMAT]) endDate: LocalDateTime?,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<*> {
        return ResponseEntity(
            transactionService.getTransactions(id, currency, startDate, endDate!!, pageable),
            HttpStatus.OK
        )
    }

    companion object {
        private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}
