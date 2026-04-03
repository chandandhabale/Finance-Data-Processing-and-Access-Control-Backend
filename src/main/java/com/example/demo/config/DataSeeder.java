package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedUsers() {
        return args -> {
            createIfNotExists("admin",   "admin@finance.com",   "Admin@123",   Role.ADMIN);
            createIfNotExists("analyst", "analyst@finance.com", "Analyst@123", Role.ANALYST);
            createIfNotExists("viewer",  "viewer@finance.com",  "Viewer@123",  Role.VIEWER);
        };
    }

    private void createIfNotExists(String username, String email, String password, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Seeded user: " + email + " [" + role + "]");
        }
    }
}
