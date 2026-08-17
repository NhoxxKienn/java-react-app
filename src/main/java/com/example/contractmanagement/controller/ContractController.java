package com.example.contractmanagement.controller;


import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.repository.ContractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    public final ContractRepository contractRepository;

    public ContractController(ContractRepository repository) {
        this.contractRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> findById(@PathVariable Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        return contractOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Contract>> findAll() {
        return ResponseEntity.ok(contractRepository.findAll());
    }
}
