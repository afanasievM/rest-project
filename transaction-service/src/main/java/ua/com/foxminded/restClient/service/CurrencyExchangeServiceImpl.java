package ua.com.foxminded.restClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.com.foxminded.restClient.dto.TransactionDto;
import ua.com.foxminded.restClient.dto.Rate;
import ua.com.foxminded.restClient.enums.Direction;
import ua.com.foxminded.restClient.exceptions.CurrencyNotFoundException;

import java.util.Currency;
import java.util.List;


@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    RateService rateService;

    @Autowired
    public CurrencyExchangeServiceImpl(RateService rateService) {
        this.rateService = rateService;
    }


    @Override
    public Page<TransactionDto> exchangeTo(Page<TransactionDto> transactions, Currency currency) {
        List<Rate> rates = rateService.getRates();
        transactions.map(t ->
                {
                    Currency transactionCurrency = Currency.getInstance(t.getCurrency());
                    if (transactionCurrency.getCurrencyCode().equals(currency.getCurrencyCode())) {
                        return t;
                    }
                    t.setCurrency(currency.getCurrencyCode());
                    return exchange(t, chooseRate(transactionCurrency, currency, rates));
                }
        );
        return transactions;
    }

    private TransactionDto exchange(TransactionDto transaction, Rate rate) {
        if (transaction.getTransactionDirection() == Direction.OUTPUT) {
            buy(transaction, rate);
        } else if (transaction.getTransactionDirection() == Direction.INPUT) {
            sell(transaction, rate);
        }
        return transaction;
    }

    private void sell(TransactionDto transaction, Rate rate) {
        Currency transactionCurrency = Currency.getInstance(transaction.getCurrency());
        double valueExchanged;
        if (transactionCurrency.getNumericCode() == rate.getCurrencyCodeB()) {
            valueExchanged = transaction.getValue() * rate.getRateSell();
        } else {
            valueExchanged = transaction.getValue() * rate.getRateBuy();
        }
        transaction.setValue(valueExchanged);
    }

    private void buy(TransactionDto transaction, Rate rate) {
        Currency transactionCurrency = Currency.getInstance(transaction.getCurrency());
        double valueExchanged;
        if (transactionCurrency.getNumericCode() == rate.getCurrencyCodeB()) {
            valueExchanged = transaction.getValue() * rate.getRateBuy();
        } else {
            valueExchanged = transaction.getValue() * rate.getRateSell();
        }
        transaction.setValue(valueExchanged);
    }

    private Rate chooseRate(Currency currencyFirst, Currency currencySecond, List<Rate> rates) {
        return rates.stream()
                .filter(rate -> {
                            int codeA = rate.getCurrencyCodeA();
                            int codeB = rate.getCurrencyCodeB();
                            int codeAExpected = currencyFirst.getNumericCode();
                            int codeBExpected = currencySecond.getNumericCode();
                            return (codeA == codeAExpected || codeA == codeBExpected) &&
                                    (codeB == codeAExpected || codeB == codeBExpected);
                        }
                ).findAny()
                .orElseThrow(() -> new CurrencyNotFoundException(currencyFirst.getSymbol(), currencySecond.getSymbol()));
    }
}



