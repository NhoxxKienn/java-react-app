package com.example.contractmanagement.dto;

import com.example.contractmanagement.model.Contract;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateContractRequest(
        @NotBlank String customer,
        @Positive BigDecimal monthlyRate,
        @NotNull LocalDate start,
        @Positive int termMonths
) {
    public static Contract to(@NonNull CreateContractRequest request) {
        return new Contract(request.customer, request.monthlyRate, request.start, request.termMonths);
    }
}
