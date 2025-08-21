package com.example.bank.service;

import com.example.bank.domain.*;
import com.example.bank.repo.AccountRepository;
import com.example.bank.repo.TransactionRepository;
import com.example.bank.repo.UserRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository txnRepository;
    private final UserRepository userRepository;

    public Account openAccount(String ownerEmail, BigDecimal initialDeposit) {
        var owner = userRepository.findByEmail(ownerEmail).orElseThrow();
        var account = Account.builder()
                .accountNumber(generateAccountNumber())
                .owner(owner)
                .balance(initialDeposit == null ? BigDecimal.ZERO : initialDeposit)
                .openedAt(OffsetDateTime.now())
                .build();
        account = accountRepository.save(account);
        if (initialDeposit != null && initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            recordTxn(account, TxnType.DEPOSIT, initialDeposit, "Initial deposit", UUID.randomUUID().toString());
        }
        return account;
    }

    public List<Account> listAccountsFor(String email) {
        var owner = userRepository.findByEmail(email).orElseThrow();
        return accountRepository.findByOwner(owner);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount, String correlationId) {
        var account = accountRepository.findById(accountId).orElseThrow();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        recordTxn(account, TxnType.DEPOSIT, amount, "Deposit", correlationId);
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount, String correlationId) {
        var account = accountRepository.findById(accountId).orElseThrow();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        recordTxn(account, TxnType.WITHDRAWAL, amount, "Withdrawal", correlationId);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount, String description, String correlationId) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("from and to cannot be same");
        var from = accountRepository.findById(fromId).orElseThrow();
        var to = accountRepository.findById(toId).orElseThrow();
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);
        recordTxn(from, TxnType.TRANSFER_OUT, amount, description == null ? "Transfer out" : description, correlationId);
        recordTxn(to, TxnType.TRANSFER_IN, amount, description == null ? "Transfer in" : description, correlationId);
    }

    public List<Transaction> statement(Long accountId) {
        var account = accountRepository.findById(accountId).orElseThrow();
        return txnRepository.findByAccountOrderByCreatedAtDesc(account);
    }

    private void recordTxn(Account account, TxnType type, BigDecimal amount, String description, String correlationId) {
        var txn = Transaction.builder()
                .account(account)
                .type(type)
                .amount(amount)
                .description(description)
                .postBalance(account.getBalance())
                .createdAt(OffsetDateTime.now())
                .correlationId(correlationId)
                .build();
        txnRepository.save(txn);
    }

    private String generateAccountNumber() {
        return "AC" + System.currentTimeMillis();
    }
}
