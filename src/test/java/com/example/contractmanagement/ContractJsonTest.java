package com.example.contractmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ContractJsonTest {
    @Autowired
    private JacksonTester<Contract> json;

    @Test
    void contractSerializationTest() throws IOException {
        Contract contract = new Contract("Alice", BigDecimal.valueOf(199.99), LocalDate.of(2025, 11, 20), 10);
        contract.setId(42L);   // set explicitly: an unsaved entity has no id

        var written = json.write(contract);

        assertThat(json.write(contract)).isStrictlyEqualToJson("expected.json");
        assertThat(written).extractingJsonPathNumberValue("@.id").isEqualTo(42);
        assertThat(written).extractingJsonPathStringValue("@.customer").isEqualTo("Alice");
        assertThat(written).extractingJsonPathNumberValue("@.termMonths").isEqualTo(10);
        assertThat(written).extractingJsonPathNumberValue("@.monthlyRate").isEqualTo(199.99);
        assertThat(written).extractingJsonPathStringValue("@.start").isEqualTo("2025-11-20");
    }

    @Test
    void unsavedContractHasNullId() throws IOException {
        Contract contract = new Contract("Bob", new BigDecimal("50.00"), LocalDate.of(2026, 1, 1), 24);

        assertThat(json.write(contract)).extractingJsonPathNumberValue("@.id").isNull();
    }

    @Test
    void deserializesFromJson() throws IOException {
        String payload = """
                {
                  "id": 7,
                  "customer": "Charlie",
                  "monthlyRate": 300.00,
                  "start": "2026-03-15",
                  "termMonths": 36
                }
                """;

        Contract parsed = json.parse(payload).getObject();

        assertThat(parsed.getCustomer()).isEqualTo("Charlie");
        assertThat(parsed.getTermMonths()).isEqualTo(36);
        assertThat(parsed.getStart()).isEqualTo(LocalDate.of(2026, 3, 15));
        assertThat(parsed.getMonthlyRate()).isEqualByComparingTo("300.00");
    }
}
