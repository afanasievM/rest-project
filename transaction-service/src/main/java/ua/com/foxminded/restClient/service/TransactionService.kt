package ua.com.foxminded.restClient.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ua.com.foxminded.restClient.dto.TransactionDto
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.List

interface TransactionService {
    fun findAllByIdAndBetweenDate(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime,
        pageable: Pageable
    ): Page<TransactionDto>

    fun findAll(): List<TransactionDto>
}
