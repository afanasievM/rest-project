package ua.com.foxminded.restClient.service;

import org.springframework.data.domain.Page;
import ua.com.foxminded.restClient.dto.TransactionDto;

import java.util.Currency;

public interface CurrencyExchangeService {
    Page<TransactionDto> exchangeTo(Page<TransactionDto> transactions, Currency currency);
}
