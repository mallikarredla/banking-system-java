package com.example.bank;

import com.example.bank.domain.Role;
import com.example.bank.domain.User;
import com.example.bank.repo.UserRepository;
import com.example.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class AccountServiceTest {

    @Autowired AccountService accountService;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void openAccount_createsWithInitialDeposit() {
        var user = User.builder()
                .email("t@t.com")
                .password(passwordEncoder.encode("pass"))
                .fullName("Test")
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        var acc = accountService.openAccount(user.getEmail(), new BigDecimal("100.00"));
        assertThat(acc.getBalance()).isEqualByComparingTo("100.00");
    }
}
