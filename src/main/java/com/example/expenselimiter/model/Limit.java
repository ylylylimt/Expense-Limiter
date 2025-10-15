package com.example.expenselimiter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "limits")
public class Limit {
    @Id
    private String id;
    private String clientId;
    private BigDecimal amount;
    private String category;
    private LocalDate dateSet;
}