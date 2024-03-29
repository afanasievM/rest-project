package com.ajaxsystems.application.service

import com.ajaxsystems.domain.dto.Rate
import com.ajaxsystems.domain.dto.TransactionDto
import com.ajaxsystems.domain.enums.Direction
import com.ajaxsystems.domain.exceptions.CurrencyNotFoundException
import java.util.Currency
import org.springframework.stereotype.Service

@Service
class CurrencyExchangeService(var rateService: GetRatesInPort) : CurrencyExchangeOutPort {

    override fun exchangeTo(transaction: TransactionDto, currency: Currency): TransactionDto {
        val transactionCurrency = Currency.getInstance(transaction.currency)
        if (transactionCurrency.currencyCode == currency.currencyCode) {
            return transaction
        }
        transaction.currency = currency.currencyCode
        return exchange(transaction, chooseRate(transactionCurrency, currency, rateService.getRates()))
    }

    private fun exchange(transaction: TransactionDto, rate: Rate): TransactionDto {
        if (transaction.transactionDirection === Direction.OUTPUT) {
            buy(transaction, rate)
        } else if (transaction.transactionDirection === Direction.INPUT) {
            sell(transaction, rate)
        }
        return transaction
    }

    private fun sell(transaction: TransactionDto, rate: Rate) {
        val transactionCurrency: Currency = Currency.getInstance(transaction.currency)
        val valueExchanged = if (transactionCurrency.numericCode == rate.currencyCodeB) {
            transaction.value?.times(rate.rateSell!!)
        } else {
            transaction.value?.times(rate.rateBuy!!)
        }
        transaction.value = valueExchanged
    }

    private fun buy(transaction: TransactionDto, rate: com.ajaxsystems.domain.dto.Rate) {
        val transactionCurrency: Currency = Currency.getInstance(transaction.currency)
        val valueExchanged = if (transactionCurrency.numericCode == rate.currencyCodeB) {
            transaction.value?.times(rate.rateBuy!!)
        } else {
            transaction.value?.times(rate.rateSell!!)
        }
        transaction.value = valueExchanged
    }

    private fun chooseRate(currencyFirst: Currency, currencySecond: Currency, rates: List<Rate>): Rate {
        return rates.stream()
            .filter { rate: Rate ->
                val codeA: Int? = rate.currencyCodeA
                val codeB: Int? = rate.currencyCodeB
                val codeAExpected = currencyFirst.numericCode
                val codeBExpected = currencySecond.numericCode
                (codeA == codeAExpected || codeA == codeBExpected) &&
                        (codeB == codeAExpected || codeB == codeBExpected)
            }.findAny()
            .orElseThrow<CurrencyNotFoundException> {
                CurrencyNotFoundException(
                    currencyFirst.symbol,
                    currencySecond.symbol
                )
            }
    }
}
