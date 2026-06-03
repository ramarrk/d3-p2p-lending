package com.p2p.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Loan;
import com.p2p.domain.model.Borrower;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryLoanRepositoryTest {

    @Test
    public void testSaveAndFindById() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoan = Mockito.mock(Loan.class);
        Mockito.when(mockLoan.getId()).thenReturn("LOAN-123");

        repository.save(mockLoan);
        Optional<Loan> result = repository.findById("LOAN-123");

        assertTrue(result.isPresent());
        assertEquals(mockLoan, result.get());
    }

    @Test
    public void testFindAll() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoan1 = Mockito.mock(Loan.class);
        Loan mockLoan2 = Mockito.mock(Loan.class);
        Mockito.when(mockLoan1.getId()).thenReturn("L1");
        Mockito.when(mockLoan2.getId()).thenReturn("L2");

        repository.save(mockLoan1);
        repository.save(mockLoan2);
        List<Loan> allLoans = repository.findAll();

        assertEquals(2, allLoans.size());
    }

    @Test
    public void testFindByBorrowerIdSuccess() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoan = Mockito.mock(Loan.class);
        Borrower mockBorrower = Mockito.mock(Borrower.class);

        Mockito.when(mockLoan.getId()).thenReturn("L1");
        Mockito.when(mockBorrower.getId()).thenReturn("B-999");
        Mockito.when(mockLoan.getBorrower()).thenReturn(mockBorrower);

        repository.save(mockLoan);
        List<Loan> result = repository.findByBorrowerId("B-999");

        assertEquals(1, result.size());
        assertEquals(mockLoan, result.get(0));
    }

    @Test
    public void testFindByBorrowerIdWithNullBorrower() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoanWithNullBorrower = Mockito.mock(Loan.class);

        Mockito.when(mockLoanWithNullBorrower.getId()).thenReturn("L2");
        Mockito.when(mockLoanWithNullBorrower.getBorrower()).thenReturn(null);

        repository.save(mockLoanWithNullBorrower);
        List<Loan> result = repository.findByBorrowerId("B-999");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByBorrowerIdWithDifferentBorrowerId() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoanWithDifferentBorrower = Mockito.mock(Loan.class);
        Borrower mockDifferentBorrower = Mockito.mock(Borrower.class);

        Mockito.when(mockLoanWithDifferentBorrower.getId()).thenReturn("L3");
        Mockito.when(mockDifferentBorrower.getId()).thenReturn("B-BERBEDA");
        Mockito.when(mockLoanWithDifferentBorrower.getBorrower()).thenReturn(mockDifferentBorrower);

        repository.save(mockLoanWithDifferentBorrower);
        List<Loan> result = repository.findByBorrowerId("B-999");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testDelete() {
        InMemoryLoanRepository repository = new InMemoryLoanRepository();
        Loan mockLoan = Mockito.mock(Loan.class);
        Mockito.when(mockLoan.getId()).thenReturn("LOAN-DEL");

        repository.save(mockLoan);
        repository.delete("LOAN-DEL");
        Optional<Loan> result = repository.findById("LOAN-DEL");

        assertFalse(result.isPresent());
    }
}