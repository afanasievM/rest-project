package com.ajaxsystems.application.useCases


import com.ajaxsystems.application.service.CurrencyExchangeService
import com.ajaxsystems.domain.dto.TransactionDto
import java.time.LocalDateTime
import java.util.Currency
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class FindTransactionsRestApiUseCase(
    private val transactionsService: TransactionsServiceOutPort,
    private val exchangeService: CurrencyExchangeService
) : FindTransactionsRestApiInputPort {

    override fun findAllByIdAndBetweenDateAndExchangeCurrency(
        id: UUID,
        start: LocalDateTime?,
        end: LocalDateTime?,
        pageable: Pageable,
        currency: String
    ): Mono<List<TransactionDto>> {
        val endDate = when {
            end == null -> LocalDateTime.now()
            end.isAfter(start) -> LocalDateTime.now()
            else -> end
        }
        return transactionsService.findAllByPersonIdAndTransactionTimeBetween(id, start!!, endDate, pageable)
            .map { exchangeService.exchangeTo(it, Currency.getInstance(currency)) }
            .collectList()

    }
}
