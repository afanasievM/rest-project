package ua.com.foxminded.courseproject.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ua.com.foxminded.courseproject.dto.TransactionDto
import java.time.LocalDateTime

interface TransactionService {
    fun getTransactions(
        id: String,
        currency: String,
        startDate: LocalDateTime?,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Page<TransactionDto>
}