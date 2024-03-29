package com.ajaxsystems.application.service

import com.ajaxsystems.domain.dto.TransactionDto
import java.util.Currency

interface CurrencyExchangeOutPort {
    fun exchangeTo(transactions: TransactionDto, currency: Currency): TransactionDto
}
