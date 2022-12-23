package ua.com.foxminded.restClient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.restClient.entity.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

    Page<Transaction> findAllByPersonIdAndTransactionTimeBetween(UUID id, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
