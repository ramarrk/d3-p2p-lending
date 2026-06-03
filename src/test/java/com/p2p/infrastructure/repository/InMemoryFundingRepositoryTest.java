package com.p2p.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Funding;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryFundingRepositoryTest {

    @Test
    public void testSaveAndFindById() {
        InMemoryFundingRepository repository = new InMemoryFundingRepository();
        Funding mockFunding = Mockito.mock(Funding.class);
        Mockito.when(mockFunding.getId()).thenReturn("FUND-001");

        repository.save(mockFunding);
        Optional<Funding> result = repository.findById("FUND-001");

        assertTrue(result.isPresent());
        assertEquals(mockFunding, result.get());
    }

    @Test
    public void testFindByLoanIdSuccess() {
        InMemoryFundingRepository repository = new InMemoryFundingRepository();
        Funding mockFunding = Mockito.mock(Funding.class);
        Mockito.when(mockFunding.getId()).thenReturn("F1");
        Mockito.when(mockFunding.getLoanId()).thenReturn("LOAN-ABC");

        repository.save(mockFunding);
        List<Funding> result = repository.findByLoanId("LOAN-ABC");

        assertEquals(1, result.size());
        assertEquals(mockFunding, result.get(0));
    }

    @Test
    public void testFindByLoanIdNotFound() {
        InMemoryFundingRepository repository = new InMemoryFundingRepository();
        Funding mockFundingWithDifferentLoan = Mockito.mock(Funding.class);
        Mockito.when(mockFundingWithDifferentLoan.getId()).thenReturn("F2");
        Mockito.when(mockFundingWithDifferentLoan.getLoanId()).thenReturn("LOAN-OTHER");

        repository.save(mockFundingWithDifferentLoan);
        List<Funding> result = repository.findByLoanId("LOAN-XYZ");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testDelete() {
        InMemoryFundingRepository repository = new InMemoryFundingRepository();
        Funding mockFunding = Mockito.mock(Funding.class);
        Mockito.when(mockFunding.getId()).thenReturn("FUND-DEL");

        repository.save(mockFunding);
        repository.delete("FUND-DEL");
        Optional<Funding> result = repository.findById("FUND-DEL");

        assertFalse(result.isPresent());
    }
}