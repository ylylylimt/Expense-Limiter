package com.example.expenselimiter.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionExceededResponse {
    private String transactionId;
    private String clientId;
    private BigDecimal transactionAmount;
    private String category;
    private LocalDateTime transactionTimestamp;
    private LocalDate limitDateSet;
    private BigDecimal limitAmount;
    private String currency = "USD";
}