package ua.com.foxminded.courseproject.service;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.courseproject.dto.TransactionDto;

import java.time.LocalDateTime;

public interface TransactionService {

    Page<TransactionDto> getTransactions(String id, String currency, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
