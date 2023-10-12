package com.ajaxsystems.application.useCases

import com.ajaxsystems.domain.dto.TransactionDto
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux

interface TransactionsServiceOutPort {
    fun findAllByPersonIdAndTransactionTimeBetween(
        id: UUID, start: LocalDateTime, end: LocalDateTime, pageable: Pageable
    ): Flux<TransactionDto>
}
