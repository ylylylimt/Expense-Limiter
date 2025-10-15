package com.example.expenselimiter.repository;

import com.example.expenselimiter.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByClientIdAndCategoryAndTimestampBetween(String clientId, String category, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByClientIdAndLimitExceededTrue(String clientId);
}