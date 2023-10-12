package ajaxsystems.application.service

import ajaxsystems.domain.dto.TransactionDto
import java.util.Currency

interface CurrencyExchangeService {
    fun exchangeTo(transactions: TransactionDto, currency: Currency): TransactionDto
}
