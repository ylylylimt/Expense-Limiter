package com.example.expenselimiter.controller;

import com.example.expenselimiter.model.Limit;
import com.example.expenselimiter.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/limits")
public class LimitController {
    
    @Autowired
    private LimitRepository limitRepository;
    
    @PostMapping
    public Limit setLimit(@RequestBody Limit limit) {
        limit.setDateSet(LocalDate.now());
        return limitRepository.save(limit);
    }
}