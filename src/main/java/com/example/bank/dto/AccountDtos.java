package com.example.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpenAccountRequest {
    @NotNull @Min(0)
    private BigDecimal initialDeposit;
}

@Data
class MoneyRequest {
    @NotNull @Min(0)
    private BigDecimal amount;
}

@Data
public class TransferRequest {
    @NotNull
    private Long fromAccountId;
    @NotNull
    private Long toAccountId;
    @NotNull @Min(0)
    private BigDecimal amount;
    private String description;
}
