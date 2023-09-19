package ua.com.foxminded.restClient.service

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.exceptions.PersonNotFoundException
import ua.com.foxminded.restClient.mapper.TransactionMapper
import ua.com.foxminded.restClient.repository.TransactionRepository


@Service
class TransactionServiceImpl(
    private val mapper: TransactionMapper,
    private val repository: TransactionRepository
) : TransactionService {

    override fun findAllByIdAndBetweenDate(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime?,
        pageable: Pageable
    ): Flux<TransactionDto> {
        val endDate = when {
            end == null -> LocalDateTime.now()
            end.isAfter(start) -> LocalDateTime.now()
            else -> end
        }
        return repository
            .findAllByPersonIdAndTransactionTimeBetween(id, start!!, endDate, pageable)
            .switchIfEmpty(Flux.error(PersonNotFoundException(id)))
            .map { mapper.entityToDto(it) }

    }
}
