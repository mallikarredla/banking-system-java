package com.example.bank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
}

@Data
class LoginRequest {
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
}

@Data
class AuthResponse {
    private String token;
    private String role;
    private String fullName;
    public AuthResponse(String token, String role, String fullName) {
        this.token = token; this.role = role; this.fullName = fullName;
    }
}
