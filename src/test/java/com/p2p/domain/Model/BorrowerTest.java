package com.p2p.domain.model;

import org.junit.jupiter.api.Test;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class BorrowerTest {

    @Test
    public void testBorrowerCreationAndGetters() {
        String id = "B1";
        String name = "Agus";
        String email = "agus@test.com";
        int creditScore = 750;
        Money limit = new Money(new BigDecimal("5000000"));

        Borrower borrower = new Borrower(id, name, email, creditScore, limit);

        assertEquals(id, borrower.getId());
        assertEquals(name, borrower.getName());
        assertEquals(email, borrower.getEmail());
        assertEquals(creditScore, borrower.getCreditScore());
        assertEquals(limit, borrower.getBorrowingLimit());
        assertEquals(0, borrower.getActiveLoanCount());
    }

    @Test
    public void testIncrementActiveLoan() {
        Borrower borrower = new Borrower("B1", "Agus", "agus@test.com", 750, new Money(BigDecimal.TEN));

        borrower.incrementActiveLoan();

        assertEquals(1, borrower.getActiveLoanCount());
    }

    @Test
    public void testDecrementActiveLoanAndBoundary() {
        Borrower borrower = new Borrower("B1", "Agus", "agus@test.com", 750, new Money(BigDecimal.TEN));

        borrower.decrementActiveLoan();
        assertEquals(0, borrower.getActiveLoanCount());

        borrower.incrementActiveLoan();
        borrower.incrementActiveLoan();

        borrower.decrementActiveLoan();
        assertEquals(1, borrower.getActiveLoanCount());
    }
}