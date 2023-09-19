package ua.com.foxminded.restClient.service

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto

interface TransactionService {
    fun findAllByIdAndBetweenDate(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime?,
        pageable: Pageable
    ): Flux<TransactionDto>

}
