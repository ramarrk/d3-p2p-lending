package com.p2p.domain.state;

import com.p2p.domain.exception.InvalidStateTransitionException;
import com.p2p.domain.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RejectedStateTest {

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan("L-REJ-001");
        loan.reject();
    }

    @Test
    void shouldBeInRejectedState_afterReject() {
        assertInstanceOf(RejectedState.class, loan.getState());
    }

    @Test
    void shouldThrow_whenApproveCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.approve());
    }

    @Test
    void shouldThrow_whenRejectCalledAgainOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.reject());
    }

    @Test
    void shouldThrow_whenStartFundingCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.startFunding());
    }

    @Test
    void shouldThrow_whenDisbursedCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.disburse());
    }

    @Test
    void shouldThrow_whenStartRepaymentCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.startRepayment());
    }

    @Test
    void shouldThrow_whenCloseCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.close());
    }

    @Test
    void shouldThrow_whenCancelCalledOnRejectedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.cancel());
    }

    @Test
    void exceptionMessage_shouldMentionRejectedState() {
        InvalidStateTransitionException ex = assertThrows(
            InvalidStateTransitionException.class,
            () -> loan.approve()
        );
        assertTrue(ex.getMessage().contains("RejectedState"));
    }

    @Test
    void getStatus_shouldReturnRejected() {
        RejectedState state = (RejectedState) loan.getState();
        assertEquals("REJECTED", state.getStatus());
    }
}