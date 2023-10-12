package ajaxsystems.application.useCases

import ajaxsystems.domain.dto.TransactionDto
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface FindTransactionsRestApiInputPort {
    fun findAllByIdAndBetweenDateAndExchangeCurrency(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime?,
        pageable: Pageable,
        currency: String
    ): Mono<List<TransactionDto>>
}
