package com.p2p.infrastructure.repository;
import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryFundingRepositoryTest {
    private InMemoryFundingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryFundingRepository();
    }

    @Test
    void shouldSaveFunding() {
        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));

        repository.save(funding);

        assertTrue(repository.findById("F001").isPresent());
    }
}