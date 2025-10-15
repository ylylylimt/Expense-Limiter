package com.example.expenselimiter.controller;

import com.example.expenselimiter.model.Transaction;
import com.example.expenselimiter.model.TransactionExceededResponse;
import com.example.expenselimiter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction, Authentication auth) {
        boolean isAuthorized = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN") || 
                                     authority.getAuthority().equals("ROLE_CLIENT_APP"));
        
        if (!isAuthorized) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only authorized users can create transactions");
        }
        return transactionService.processTransaction(transaction);
    }
    
    @GetMapping("/exceeded/{clientId}")
    public List<TransactionExceededResponse> getExceededTransactions(@PathVariable String clientId, Authentication auth) {
        String userClientId = (String) auth.getDetails();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        if (!isAdmin && !clientId.equals(userClientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        
        return transactionService.getExceededTransactions(clientId);
    }
}