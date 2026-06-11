package com.p2p.domain;

import com.p2p.domain.exception.InvalidStateTransitionException;
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

    @Test
void shouldTransitionToRejected_whenRejectedFromPending() {
    Loan loan = new Loan("L001");
    loan.reject();
    assertInstanceOf(RejectedState.class, loan.getState());
}

@Test
void shouldThrowException_whenApprovingRejectedLoan() {
    Loan loan = new Loan("L001");
    loan.reject();
    assertThrows(InvalidStateTransitionException.class, () -> loan.approve());
}

@Test
void shouldThrowException_whenStartFundingFromRejected() {
    Loan loan = new Loan("L001");
    loan.reject();
    assertThrows(InvalidStateTransitionException.class, () -> loan.startFunding());
}



@Test
void shouldTransitionToClosed_whenClosedFromRepayment() {
    Loan loan = new Loan("L001");
    loan.approve();
    loan.startFunding();
    loan.disburse();
    loan.startRepayment();
    loan.close();
    assertInstanceOf(ClosedState.class, loan.getState());
}

// Task 3
@Test
void shouldThrow_whenClosedDirectlyFromPending() {
    Loan loan = new Loan("L001");
    assertThrows(InvalidStateTransitionException.class, () -> loan.close());
}

@Test
void shouldThrow_whenDisburseFromApproved_skippingFunding() {
    Loan loan = new Loan("L001");
    loan.approve();
    assertThrows(InvalidStateTransitionException.class, () -> loan.disburse());
}

@Test
void shouldThrow_whenCloseDirectlyFromDisbursed_skippingRepayment() {
    Loan loan = new Loan("L001");
    loan.approve();
    loan.startFunding();
    loan.disburse();
    assertThrows(InvalidStateTransitionException.class, () -> loan.close());
}

}