package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.entity.Transaction
import ua.com.foxminded.restClient.enums.Direction
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
    ): Page<TransactionDto> {
        val transactions = repository.findAllByPersonIdAndTransactionTimeBetween(id, start!!, end, pageable)
        if (transactions.isEmpty) {
            throw PersonNotFoundException(id)
        }
        return transactions.map { transaction: Transaction? ->
            mapper.entityToDto(
                transaction!!
            )
        }
    }
}
