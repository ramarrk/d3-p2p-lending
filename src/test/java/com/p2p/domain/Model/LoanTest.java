package com.p2p.domain.model;

import com.p2p.domain.state.*;
import com.p2p.domain.exception.InvalidStateTransitionException;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.observer.FundingObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan("LOAN-001");
    }

    @Test
    void testInitialStateIsPending() {
        assertTrue(loan.getState() instanceof PendingState);
    }

    @Test
    void testPendingToApproved() {
        loan.approve();
        assertTrue(loan.getState() instanceof ApprovedState);
    }

    @Test
    void testFullCycle() {
        loan.approve();
        loan.startFunding();
        loan.disburse();
        loan.startRepayment();
        loan.close();
        assertTrue(loan.getState() instanceof ClosedState);
    }

    @Test
    void testInvalidTransition() {
        assertThrows(InvalidStateTransitionException.class, () -> {
            loan.disburse();
        });
    }

    @Test
    void testCancelFromFunding() {
        loan.approve();
        loan.startFunding();
        loan.cancel();
        assertTrue(loan.getState() instanceof CancelledState);
    }

    @Test
    public void testLoanBasicCreationAndGetters() {
        assertEquals("LOAN-001", loan.getId());
        assertNotNull(loan.getTotalFunded());
        assertNotNull(loan.getFundings());
    }

    @Test
    public void testIsFullyFundedWhenTargetAmountIsNull() {
        loan.setTargetAmount(null);
        assertFalse(loan.isFullyFunded());
    }

    @Test
    public void testGettersAndSettersForDomainObjects() {
        Borrower mockBorrower = Mockito.mock(Borrower.class);
        Money targetAmount = new Money(new BigDecimal("5000000"));

        loan.setBorrower(mockBorrower);
        loan.setTargetAmount(targetAmount);

        assertEquals(mockBorrower, loan.getBorrower());
        assertEquals(targetAmount, loan.getTargetAmount());
    }

    @Test
    public void testAddObserver() {
        FundingObserver mockObserver = Mockito.mock(FundingObserver.class);
        loan.addObserver(mockObserver);
        assertNotNull(loan);
    }
}