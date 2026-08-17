package com.example.contractmanagement.config;


import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.repository.ContractRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataSeeder {
    @Bean
    public CommandLineRunner demo(ContractRepository repository) {
        return (args) -> {
            // save a few contracts
            repository.save(new Contract("Jack Bauer", BigDecimal.ONE, LocalDate.now(), 12));
            repository.save(new Contract("Kim Palmer", BigDecimal.valueOf(12), LocalDate.now(), 10));
        };
    }
}
