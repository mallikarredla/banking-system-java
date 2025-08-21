package com.example.bank.controller;

import com.example.bank.domain.Account;
import com.example.bank.domain.Transaction;
import com.example.bank.dto.OpenAccountRequest;
import com.example.bank.dto.TransferRequest;
import com.example.bank.dto.MoneyRequest;
import com.example.bank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Account> open(@AuthenticationPrincipal UserDetails me,
                                        @RequestBody @Valid OpenAccountRequest req) {
        var acc = accountService.openAccount(me.getUsername(), req.getInitialDeposit());
        return ResponseEntity.ok(acc);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> myAccounts(@AuthenticationPrincipal UserDetails me) {
        return ResponseEntity.ok(accountService.listAccountsFor(me.getUsername()));
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody @Valid MoneyRequest req) {
        accountService.deposit(id, req.getAmount(), UUID.randomUUID().toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody @Valid MoneyRequest req) {
        accountService.withdraw(id, req.getAmount(), UUID.randomUUID().toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfers")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest req) {
        accountService.transfer(req.getFromAccountId(), req.getToAccountId(), req.getAmount(), req.getDescription(), UUID.randomUUID().toString());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts/{id}/statement")
    public ResponseEntity<List<Transaction>> statement(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.statement(id));
    }
}
