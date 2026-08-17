package com.example.contractmanagement.dto;

import com.example.contractmanagement.model.Contract;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractResponse(
        Long id,
        String customer,
        BigDecimal monthlyRate,
        LocalDate start,
        int termMonths
) {
    public static ContractResponse from(@NonNull Contract contract) {
        return new ContractResponse(
                contract.getId(),
                contract.getCustomer(),
                contract.getMonthlyRate(),
                contract.getStart(),
                contract.getTermMonths());
    }
}