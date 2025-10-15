package com.example.expenselimiter.repository;

import com.example.expenselimiter.model.Limit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LimitRepository extends MongoRepository<Limit, String> {
    Optional<Limit> findFirstByClientIdAndCategory(String clientId, String category);
}