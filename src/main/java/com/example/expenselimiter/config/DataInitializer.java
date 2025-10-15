package com.example.expenselimiter.config;

import com.example.expenselimiter.model.Client;
import com.example.expenselimiter.model.User;
import com.example.expenselimiter.repository.ClientRepository;
import com.example.expenselimiter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(List.of("ADMIN"));
            admin.setApiKey(UUID.randomUUID().toString());
            admin.setClientId("admin");
            userRepository.save(admin);
            
            // Create client user
            User client = new User();
            client.setUsername("client123");
            client.setPassword(passwordEncoder.encode("client123"));
            client.setRoles(List.of("CLIENT"));
            client.setApiKey(UUID.randomUUID().toString());
            client.setClientId("client123");
            userRepository.save(client);
            
            // Create client app user
            User clientApp = new User();
            clientApp.setUsername("clientapp");
            clientApp.setPassword(passwordEncoder.encode("app123"));
            clientApp.setRoles(List.of("CLIENT_APP"));
            clientApp.setApiKey(UUID.randomUUID().toString());
            clientApp.setClientId("clientapp");
            userRepository.save(clientApp);
            
            System.out.println("Test users created:");
            System.out.println("Admin: admin/admin123");
            System.out.println("Client: client123/client123");
            System.out.println("Client App: clientapp/app123");
        }
        
        if (clientRepository.count() == 0) {
            Client client1 = new Client();
            client1.setId("client123");
            client1.setName("Test Client 123");
            client1.setEmail("client123@example.com");
            clientRepository.save(client1);
            
            Client client2 = new Client();
            client2.setId("admin");
            client2.setName("Admin Client");
            client2.setEmail("admin@example.com");
            clientRepository.save(client2);
            
            System.out.println("Test clients created: client123, admin");
        }
    }
}