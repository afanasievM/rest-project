package ua.com.foxminded.restClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.restClient.dto.TransactionDto;
import ua.com.foxminded.restClient.entity.Transaction;
import ua.com.foxminded.restClient.exceptions.PersonNotFoundException;
import ua.com.foxminded.restClient.mapper.TransactionMapper;
import ua.com.foxminded.restClient.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionMapper mapper;
    private TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionMapper mapper, TransactionRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Page<TransactionDto> findAllByIdAndBetweenDate(UUID id, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<Transaction> transactions = repository.findAllByPersonIdAndTransactionTimeBetween(id, start, end, pageable);
        if (transactions.isEmpty()) {
            throw new PersonNotFoundException(id);
        }
        return transactions.map(mapper::entityToDto);
    }

}
