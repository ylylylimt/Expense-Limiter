package com.example.expenselimiter.repository;

import com.example.expenselimiter.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
}