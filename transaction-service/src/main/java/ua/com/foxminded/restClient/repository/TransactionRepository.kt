package ua.com.foxminded.restClient.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ua.com.foxminded.restClient.entity.Transaction
import java.time.LocalDateTime
import java.util.*

@Repository
interface TransactionRepository : CrudRepository<Transaction, UUID> {
    fun findAllByPersonIdAndTransactionTimeBetween(
        id: UUID, start: LocalDateTime, end: LocalDateTime?, pageable: Pageable
    ): Page<Transaction>
}
