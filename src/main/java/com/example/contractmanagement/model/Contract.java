package com.example.contractmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String customer;
    private BigDecimal monthlyRate;
    private LocalDate start;
    private int termMonths;

    protected Contract() {}

    public Contract(String customer, BigDecimal monthlyRate, LocalDate start, int termMonths) {
        this.customer = customer;
        this.monthlyRate = monthlyRate;
        this.start = start;
        this.termMonths = termMonths;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setMonthlyRate(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public BigDecimal getMonthlyRate() {
        return monthlyRate;
    }

    public LocalDate getStart() {
        return start;
    }

    public int getTermMonths() {
        return termMonths;
    }
}
