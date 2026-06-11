package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryLoanRepositoryTest {
    private InMemoryLoanRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLoanRepository();
    }

    @Test
    void shouldSaveLoan() {
        Loan loan = new Loan("L001");
        repository.save(loan);
        assertTrue(repository.findById("L001").isPresent());
    }

    @Test
    void shouldFindAllLoans() {
        repository.save(new Loan("L001"));
        repository.save(new Loan("L002"));
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldDeleteLoan() {
        Loan loan = new Loan("L001");
        repository.save(loan);
        repository.delete("L001");
        assertFalse(repository.findById("L001").isPresent());
    }
}