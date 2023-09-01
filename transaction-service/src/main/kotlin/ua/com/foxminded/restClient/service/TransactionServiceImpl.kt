package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.exceptions.PersonNotFoundException
import ua.com.foxminded.restClient.mapper.TransactionMapper
import ua.com.foxminded.restClient.repository.TransactionRepository
import java.time.LocalDateTime
import java.util.*


@Service
class TransactionServiceImpl @Autowired constructor(
    private val mapper: TransactionMapper,
    private val repository: TransactionRepository
) : TransactionService {

    override fun findAllByIdAndBetweenDate(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime,
        pageable: Pageable
    ): Flux<TransactionDto> {
        return repository
            .findAllByPersonIdAndTransactionTimeBetween(id, start!!, end, pageable)
            .switchIfEmpty(Flux.error(PersonNotFoundException(id)))
            .map { mapper.entityToDto(it) }

    }
}
