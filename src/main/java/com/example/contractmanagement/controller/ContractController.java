package com.example.contractmanagement.controller;


import com.example.contractmanagement.dto.ContractResponse;
import com.example.contractmanagement.dto.ContractRequest;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.repository.ContractRepository;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractRepository contractRepository;

    public ContractController(ContractRepository repository) {
        this.contractRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> findById(@PathVariable Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        return contractOptional.map(ContractResponse::from).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<ContractResponse>> findAll() {
        return ResponseEntity.ok(contractRepository.findAll().stream().map(ContractResponse::from).toList());
    }

    @PostMapping()
    public ResponseEntity<Void> createContract(
            @RequestBody  @Valid ContractRequest contractRequest,
            UriComponentsBuilder ucb) {
        Contract saved = contractRepository
                .save(ContractRequest.to(contractRequest));
        URI locationOfNewContract = ucb.path("/api/contracts/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(locationOfNewContract).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContract(
            @PathVariable Long id,
            @RequestBody @Valid ContractRequest request
    ) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            Contract updated = contractOptional.get();
            updated.update(ContractRequest.to(request));
            contractRepository.save(updated);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
