package ua.com.foxminded.courseproject.service

import java.time.LocalDateTime
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.TransactionDto

interface TransactionService {
    fun getTransactions(
        id: String,
        currency: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Mono<List<TransactionDto>>
}

