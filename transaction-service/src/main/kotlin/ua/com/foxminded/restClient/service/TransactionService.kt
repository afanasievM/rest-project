package ua.com.foxminded.restClient.service

import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import java.time.LocalDateTime
import java.util.*

interface TransactionService {
    fun findAllByIdAndBetweenDate(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime,
        pageable: Pageable
    ): Flux<TransactionDto>

}
