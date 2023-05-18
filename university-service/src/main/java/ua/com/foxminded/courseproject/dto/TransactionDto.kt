package ua.com.foxminded.courseproject.dto

import org.springframework.format.annotation.DateTimeFormat
import ua.com.foxminded.courseproject.enums.Direction
import java.time.LocalDateTime
import java.util.*

data class TransactionDto(
    var id: UUID? = UUID.randomUUID(),
    var personId: UUID? = null,

    @field:DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var transactionTime: LocalDateTime? = null,
    var transactionDirection: Direction? = null,
    var value: Double? = null,
    var currency: String? = null,
    var iban: String? = null
)
