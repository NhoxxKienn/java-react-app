package com.example.contractmanagement;

import com.example.contractmanagement.controller.ContractController;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractController.class)
public class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContractRepository repository;

    private Contract sampleContract() {
        Contract c = new Contract("Jack Bauer", new BigDecimal("199.99"), LocalDate.of(2026, 1, 1), 24);
        c.setId(1L);
        return c;
    }

    @Test
    void returnsContractWhenFound() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.of(sampleContract()));

        mockMvc.perform(get("/api/contracts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customer").value("Jack Bauer"))
                .andExpect(jsonPath("$.monthlyRate").value(199.99))
                .andExpect(jsonPath("$.termMonths").value(24));
    }

    @Test
    void returnsNotFoundForUnknownId() throws Exception {
        given(repository.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/contracts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsAllContracts() throws Exception {
        given(repository.findAll()).willReturn(List.of(sampleContract()));

        mockMvc.perform(get("/api/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customer").value("Jack Bauer"));
    }

    @Test
    void returnsEmptyArrayWhenNoContracts() throws Exception {
        given(repository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/api/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateANewContract() throws Exception {
        Contract saved = sampleContract();
        given(repository.save(any(Contract.class))).willReturn(saved);

        String payload = """
                    {
                        "customer": "Jack Bauer",
                        "monthlyRate": 199.99,
                        "start": "2026-01-01",
                        "termMonths": 24
                    }
                """;

        mockMvc.perform(post("/api/contracts")
                        .contentType("application/json")
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/contracts/1"));
    }

    @Test
    void rejectsInvalidContract() throws Exception {
        String payload ="""
                    {
                        "customer": "",
                        "monthlyRate": -50,
                        "start": "2026-01-01",
                        "termMonths": 0
                    }
                """;
        mockMvc.perform(post("/api/contracts")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateExistingContract() throws Exception {
        Contract existing = sampleContract();
        given(repository.findById(1L)).willReturn(Optional.of(existing));
        given(repository.save(any(Contract.class))).willReturn(existing);

        String payload = """
            {
                "customer": "Kim Palmer",
                "monthlyRate": 250.00,
                "start": "2026-03-01",
                "termMonths": 36
            }
            """;

        mockMvc.perform(put("/api/contracts/1")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isNoContent());
    }

    @Test
    void returnsNotFoundWhenUpdatingUnknownId() throws Exception {
        given(repository.findById(999L)).willReturn(Optional.empty());

        String payload = """
            {
                "customer": "Kim Palmer",
                "monthlyRate": 250.00,
                "start": "2026-03-01",
                "termMonths": 36
            }
            """;

        mockMvc.perform(put("/api/contracts/999")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExistingContract() throws Exception {
        Contract existing = sampleContract();
        given(repository.findById(1L)).willReturn(Optional.of(existing));
        given(repository.save(any(Contract.class))).willReturn(existing);
        given(repository.existsById(1L)).willReturn(true);

        mockMvc.perform(delete("/api/contracts/1"))
                .andExpect(status().isNoContent());

        verify(repository).deleteById(1L);
    }

    @Test
    void returnsNotFoundWhenDeletingUnknownId() throws Exception {
        given(repository.existsById(999L)).willReturn(false);

        mockMvc.perform(delete("/api/contracts/999"))
                .andExpect(status().isNotFound());

        verify(repository, never()).deleteById(999L);
    }
}
