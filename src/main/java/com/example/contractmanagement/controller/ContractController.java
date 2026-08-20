package com.example.contractmanagement.controller;


import com.example.contractmanagement.dto.ContractResponse;
import com.example.contractmanagement.dto.ContractRequest;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService service) {
        this.contractService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> findById(@PathVariable Long id) {
        return contractService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<ContractResponse>> findAll() {
        return ResponseEntity.ok(contractService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Void> createContract(
            @RequestBody  @Valid ContractRequest contractRequest,
            UriComponentsBuilder ucb) {
        Contract saved = contractService.create(contractRequest);
        URI locationOfNewContract = ucb.path("/api/contracts/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(locationOfNewContract).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContract(
            @PathVariable Long id,
            @RequestBody @Valid ContractRequest request
    ) {
        if (contractService.update(id, request)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        if (contractService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
