package ajaxsystems.infrastructure.database.mongodb.repository

import ajaxsystems.infrastructure.database.mongodb.entity.Transaction
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransactionRepository : ReactiveSortingRepository<Transaction, UUID> {
    fun findAllByPersonIdAndTransactionTimeBetween(
        id: UUID, start: LocalDateTime, end: LocalDateTime?, pageable: Pageable
    ): Flux<Transaction>
}
