package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Lender;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryLenderRepositoryTest {
    private InMemoryLenderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLenderRepository();
    }

    @Test
    void shouldSaveLender() {
        Lender lender = new Lender("LN001", "John", "john@mail.com",
                new Money(new BigDecimal("10000000")));
        repository.save(lender);

        assertTrue(repository.findById("LN001").isPresent());
    }

    @Test
    void shouldFindAllLenders() {
        Lender lender1 = new Lender("LN001", "John", "john@mail.com",
                new Money(new BigDecimal("10000000")));
        Lender lender2 = new Lender("LN002", "Jane", "jane@mail.com",
                new Money(new BigDecimal("5000000")));
        repository.save(lender1);
        repository.save(lender2);

        List<Lender> result = repository.findAll();

        assertEquals(2, result.size());
    }
}