package com.p2p.infrastructure.repository;
import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

        assertDoesNotThrow(() -> repository.save(funding));
    }

    @Test
    void shouldFindFundingById() {
        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));
        repository.save(funding);

        Optional<Funding> result = repository.findById("F001");

        assertTrue(result.isPresent());
        assertEquals("F001", result.get().getId());
    }

    @Test
    void shouldFindFundingByLoanId() {
        Funding funding1 = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));
        Funding funding2 = new Funding("F002", "L001", "LN002",
                new Money(new BigDecimal("3000000")));
        repository.save(funding1);
        repository.save(funding2);

        List<Funding> result = repository.findByLoanId("L001");

        assertEquals(2, result.size());
    }

    @Test
    void shouldDeleteFunding() {
        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));
        repository.save(funding);

        repository.delete("F001");

        assertFalse(repository.findById("F001").isPresent());
    }
}