package com.example.contractmanagement.controller;


import com.example.contractmanagement.dto.ContractResponse;
import com.example.contractmanagement.dto.CreateContractRequest;
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
            @RequestBody  @Valid CreateContractRequest createContractRequest,
            UriComponentsBuilder ucb) {
        Contract saved = contractRepository
                .save(CreateContractRequest.to(createContractRequest));
        URI locationOfNewContract = ucb.path("/api/contracts/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(locationOfNewContract).build();
    }
}
