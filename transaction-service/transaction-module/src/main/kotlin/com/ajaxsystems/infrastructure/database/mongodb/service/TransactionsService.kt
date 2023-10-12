package com.ajaxsystems.infrastructure.database.mongodb.service

import com.ajaxsystems.application.useCases.TransactionsServiceOutPort
import com.ajaxsystems.domain.dto.TransactionDto
import com.ajaxsystems.infrastructure.database.mongodb.repository.TransactionRepository
import com.ajaxsystems.infrastructure.mapper.TransactionMapper
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TransactionsService(
    private val repository: TransactionRepository,
    private val mapper: TransactionMapper
) : TransactionsServiceOutPort {
    override fun findAllByPersonIdAndTransactionTimeBetween(
        id: UUID,
        start: LocalDateTime,
        end: LocalDateTime,
        pageable: Pageable
    ): Flux<TransactionDto> {
        return repository.findAllByPersonIdAndTransactionTimeBetween(id, start, end, pageable)
            .map { mapper.entityToDto(it) }
    }
}
