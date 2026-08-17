package com.example.contractmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ContractmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractmanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ContractRepository repository) {
		return (args) -> {
			// save a few contracts
			repository.save(new Contract("Jack Bauer", BigDecimal.ONE, LocalDate.now(), 12));
			repository.save(new Contract("Kim Palmer", BigDecimal.valueOf(12), LocalDate.now(), 10));
		};
	}
}
