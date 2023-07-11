package ua.com.foxminded.restClient.service

import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.TransactionDto
import java.util.*

interface CurrencyExchangeService {
    fun exchangeTo(transactions: TransactionDto, currency: Currency): TransactionDto
}