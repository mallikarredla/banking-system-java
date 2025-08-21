package com.example.bank.controller;

import com.example.bank.domain.Role;
import com.example.bank.domain.User;
import com.example.bank.dto.RegisterRequest;
import com.example.bank.dto.AuthResponse;
import com.example.bank.dto.LoginRequest;
import com.example.bank.repo.UserRepository;
import com.example.bank.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        var user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user.getEmail(), Map.of("role", user.getRole().name(), "name", user.getFullName()));
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getFullName()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
        var auth = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        authenticationManager.authenticate(auth);
        var user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user.getEmail(), Map.of("role", user.getRole().name(), "name", user.getFullName()));
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getFullName()));
    }
}
