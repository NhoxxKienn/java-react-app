package com.example.contractmanagement.service;

import com.example.contractmanagement.dto.ContractRequest;
import com.example.contractmanagement.dto.ContractResponse;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.repository.ContractRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    private final ContractRepository repository;

    public ContractService(ContractRepository repository) {
        this.repository = repository;
    }

    public Optional<ContractResponse> findById(Long id) {
        return repository.findById(id).map(ContractResponse::from);
    }

    public List<ContractResponse> findAll() {
        return repository.findAll().stream().map(ContractResponse::from).toList();
    }

    public Contract create(ContractRequest request) {
        return repository.save(ContractRequest.to(request));
    }

    public boolean update(Long id, ContractRequest request) {
        Optional<Contract> contractOptional = repository.findById(id);
        if (contractOptional.isPresent()) {
            Contract updated = contractOptional.get();
            updated.update(ContractRequest.to(request));
            repository.save(updated);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
