package com.p2p.domain;

import com.p2p.domain.model.Loan;
import com.p2p.domain.state.*;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class LoanLifecycleTest {
    @Test
    void shouldStartInPendingState() {
        Loan loan = new Loan("L001");
        assertInstanceOf(PendingState.class, loan.getState());
    }

    @Test
    void shouldTransitionToApproved_whenApproved() {
        Loan loan = new Loan("L001");
        loan.approve();
        assertInstanceOf(ApprovedState.class, loan.getState());
    }

    @Test
    void shouldTransitionToFunding_whenStartFunding() {
        Loan loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        assertInstanceOf(FundingState.class, loan.getState());
    }

    @Test
    void shouldTransitionToDisbursed_whenDisburse() {
        Loan loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        loan.disburse();
        assertInstanceOf(DisbursedState.class, loan.getState());
    }

    @Test
    void shouldThrowException_whenDisburseDirectlyFromPending() {
        Loan loan = new Loan("L001");
        assertThrows(com.p2p.domain.exception.InvalidStateTransitionException.class, () -> {
            loan.disburse();
        });
    }
}