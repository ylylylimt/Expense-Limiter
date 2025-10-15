package com.example.expenselimiter.service;

import com.example.expenselimiter.model.Limit;
import com.example.expenselimiter.model.Transaction;
import com.example.expenselimiter.model.TransactionExceededResponse;
import com.example.expenselimiter.repository.LimitRepository;
import com.example.expenselimiter.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private LimitRepository limitRepository;
    
    private static final BigDecimal DEFAULT_LIMIT = new BigDecimal("1000");
    
    public Transaction processTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        
        BigDecimal monthlySpent = getMonthlySpent(transaction.getClientId(), transaction.getCategory());
        BigDecimal limit = getLimit(transaction.getClientId(), transaction.getCategory());
        
        transaction.setLimitExceeded(monthlySpent.add(transaction.getAmount()).compareTo(limit) > 0);
        
        return transactionRepository.save(transaction);
    }
    
    private BigDecimal getMonthlySpent(String clientId, String category) {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime start = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime end = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        
        List<Transaction> transactions = transactionRepository.findByClientIdAndCategoryAndTimestampBetween(
            clientId, category, start, end);
        
        return transactions.stream()
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal getLimit(String clientId, String category) {
        return limitRepository.findFirstByClientIdAndCategory(clientId, category)
            .map(Limit::getAmount)
            .orElse(DEFAULT_LIMIT);
    }
    
    public List<TransactionExceededResponse> getExceededTransactions(String clientId) {
        List<Transaction> exceededTransactions = transactionRepository.findByClientIdAndLimitExceededTrue(clientId);
        
        return exceededTransactions.stream()
            .map(this::mapToExceededResponse)
            .toList();
    }
    
    private TransactionExceededResponse mapToExceededResponse(Transaction transaction) {
        TransactionExceededResponse response = new TransactionExceededResponse();
        response.setTransactionId(transaction.getId());
        response.setClientId(transaction.getClientId());
        response.setTransactionAmount(transaction.getAmount());
        response.setCategory(transaction.getCategory());
        response.setTransactionTimestamp(transaction.getTimestamp());
        
        limitRepository.findFirstByClientIdAndCategory(transaction.getClientId(), transaction.getCategory())
            .ifPresentOrElse(
                limit -> {
                    response.setLimitDateSet(limit.getDateSet());
                    response.setLimitAmount(limit.getAmount());
                },
                () -> {
                    response.setLimitDateSet(null);
                    response.setLimitAmount(DEFAULT_LIMIT);
                }
            );
        
        return response;
    }
}