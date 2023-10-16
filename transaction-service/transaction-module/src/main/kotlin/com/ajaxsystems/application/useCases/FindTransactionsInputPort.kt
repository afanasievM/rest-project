package com.ajaxsystems.application.useCases

import com.ajaxsystems.domain.dto.TransactionDto
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface FindTransactionsInputPort {
    fun findAllByIdAndBetweenDateAndExchangeCurrency(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime?,
        pageable: Pageable,
        currency: String
    ): Mono<List<TransactionDto>>
}
