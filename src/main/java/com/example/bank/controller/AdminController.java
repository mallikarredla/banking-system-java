package com.example.bank.controller;

import com.example.bank.domain.Account;
import com.example.bank.domain.User;
import com.example.bank.repo.AccountRepository;
import com.example.bank.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> accounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }
}
