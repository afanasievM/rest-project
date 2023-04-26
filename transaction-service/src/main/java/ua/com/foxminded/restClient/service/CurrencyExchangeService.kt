package ua.com.foxminded.restClient.service

import org.springframework.data.domain.Page
import ua.com.foxminded.restClient.dto.TransactionDto
import java.util.*

interface CurrencyExchangeService {
    fun exchangeTo(transactions: Page<TransactionDto>, currency: Currency): Page<TransactionDto>
}