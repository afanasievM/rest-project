package ua.com.foxminded.restClient.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.restClient.dto.TransactionDto;

import java.time.LocalDateTime;
import java.util.UUID;


public interface TransactionService {

    Page<TransactionDto> findAllByIdAndBetweenDate(UUID id, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
