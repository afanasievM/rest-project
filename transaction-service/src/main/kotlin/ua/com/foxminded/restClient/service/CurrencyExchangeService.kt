package ua.com.foxminded.restClient.service

import java.util.Currency
import ua.com.foxminded.restClient.dto.TransactionDto

interface CurrencyExchangeService {
    fun exchangeTo(transactions: TransactionDto, currency: Currency): TransactionDto
}
