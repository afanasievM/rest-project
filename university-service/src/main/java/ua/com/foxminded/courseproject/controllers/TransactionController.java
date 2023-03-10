package ua.com.foxminded.courseproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ua.com.foxminded.courseproject.service.TransactionService;
import ua.com.foxminded.courseproject.service.TransactionServiceImpl;
import ua.com.foxminded.courseproject.utils.PageableTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@RestController
public class TransactionController {
    private final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get all transaction by ID with currency between dates.")
    @ApiResponse(responseCode = "200", description = "Person is found.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageableTransaction.class))})
    @GetMapping(value = "transactions/{id}/{currency}")
    public ResponseEntity getTransactions(
            @PathVariable(name = "id", required = true) String id,
            @PathVariable(name = "currency", required = true) String currency,
            @RequestParam(name = "startdate", required = true)
            @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime startDate,
            @RequestParam(name = "enddate", required = false)
            @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime endDate,
            @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(transactionService.getTransactions(id, currency, startDate, endDate, pageable), HttpStatus.OK);
    }


}
