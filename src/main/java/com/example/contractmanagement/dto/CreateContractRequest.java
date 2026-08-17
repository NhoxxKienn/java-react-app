package com.example.contractmanagement.dto;

import com.example.contractmanagement.model.Contract;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateContractRequest(
        String customer,
        BigDecimal monthlyRate,
        LocalDate start,
        int termMonths
) {
}
