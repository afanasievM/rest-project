package ua.com.foxminded.restClient.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxminded.restClient.dto.TransactionDto;
import ua.com.foxminded.restClient.service.CurrencyExchangeService;
import ua.com.foxminded.restClient.service.TransactionService;
import ua.com.foxminded.restClient.utils.PageableTransaction;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@RestController
public class TransactionController {
    private TransactionService transactionService;
    private CurrencyExchangeService exchangeService;

    @Autowired
    public TransactionController(TransactionService transactionService, CurrencyExchangeService exchangeService) {
        this.transactionService = transactionService;
        this.exchangeService = exchangeService;
    }


    @Operation(summary = "Get all transaction by ID with currency between dates.")
    @ApiResponse(responseCode = "200", description = "Person is found.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageableTransaction.class))})
    @GetMapping(value = "transactions/{id}/{currency}")
    public ResponseEntity getTransactions(
            @PathVariable(name = "id", required = true) UUID id,
            @PathVariable(name = "currency", required = true) String currency,
            @RequestParam(name = "startdate", required = true)
            @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam(name = "enddate", required = false)
            @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @ParameterObject @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<TransactionDto> transactions = findTransactions(id, startDate, endDate, pageable);
        return new ResponseEntity(exchangeService.exchangeTo(transactions, Currency.getInstance(currency)), HttpStatus.OK);
    }

    private Page<TransactionDto> findTransactions(UUID id, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<TransactionDto> transactions;
        if (endDate != null && endDate.isAfter(startDate)) {
            transactions = transactionService.findAllByIdAndBetweenDate(id, startDate, endDate, pageable);
        } else {
            transactions = transactionService.findAllByIdAndBetweenDate(id, startDate, LocalDateTime.now(), pageable);
        }
        return transactions;
    }

}
