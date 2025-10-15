package com.example.expenselimiter.controller;

import com.example.expenselimiter.model.User;
import com.example.expenselimiter.repository.UserRepository;
import com.example.expenselimiter.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getUsername(), user.getClientId(), user.getRoles());
        return new LoginResponse(token);
    }
    
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
    
    @Data
    public static class LoginResponse {
        private String token;
        
        public LoginResponse(String token) {
            this.token = token;
        }
    }
}