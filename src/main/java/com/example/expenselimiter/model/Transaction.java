package com.example.expenselimiter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String clientId;
    private BigDecimal amount;
    private String category; // goods or services
    private LocalDateTime timestamp;
    private boolean limitExceeded;
}