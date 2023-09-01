package ua.com.foxminded.restClient.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class TransactionNatsDto(
    var id: UUID? = null,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
    var currency: String? = null,
    var iban: String? = null,
    var page: Int? = null,
    var size: Int? = null
)