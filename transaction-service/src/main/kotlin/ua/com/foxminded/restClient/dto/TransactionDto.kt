package ua.com.foxminded.restClient.dto

import ua.com.foxminded.restClient.enums.Direction
import java.time.LocalDateTime
import java.util.UUID

data class TransactionDto(
    var id: UUID? = null,
    var personId: UUID? = null,
    var transactionTime: LocalDateTime? = null,
    var transactionDirection: Direction? = null,
    var value: Double? = null,
    var currency: String? = null,
    var iban: String? = null
)
