package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import ua.com.foxminded.restClient.dto.Rate
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.enums.Direction
import ua.com.foxminded.restClient.exceptions.CurrencyNotFoundException
import java.util.*

@Service
class CurrencyExchangeServiceImpl @Autowired constructor(var rateService: RateService) : CurrencyExchangeService {

    override fun exchangeTo(transactions: Page<TransactionDto>, currency: Currency): Page<TransactionDto> {
        val rates = rateService.rates
        transactions.content
            .map { t ->
                val transactionCurrency = Currency.getInstance(t.currency)
                if (transactionCurrency.currencyCode == currency.currencyCode) {
                    return@map t
                }
                t.currency = currency.currencyCode
                exchange(t, chooseRate(transactionCurrency, currency, rates))
            }
        return transactions
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

    private fun buy(transaction: TransactionDto, rate: Rate) {
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