package com.example.bank.service;

import com.example.bank.domain.Role;
import com.example.bank.domain.User;
import com.example.bank.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seed() {
        if (!userRepository.existsByEmail("admin@bank.local")) {
            userRepository.save(User.builder()
                    .email("admin@bank.local")
                    .password(passwordEncoder.encode("Admin@123"))
                    .fullName("Bank Admin")
                    .role(Role.ADMIN)
                    .build());
        }
        if (!userRepository.existsByEmail("demo@bank.local")) {
            userRepository.save(User.builder()
                    .email("demo@bank.local")
                    .password(passwordEncoder.encode("Demo@123"))
                    .fullName("Demo User")
                    .role(Role.CUSTOMER)
                    .build());
        }
    }
}
