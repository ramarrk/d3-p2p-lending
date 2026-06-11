package com.p2p.domain.state;

import com.p2p.domain.exception.InvalidStateTransitionException;
import com.p2p.domain.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClosedStateTest {

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan("L-CLOSED-001");
        loan.approve();
        loan.startFunding();
        loan.disburse();
        loan.startRepayment();
        loan.close();
    }

    @Test
    void shouldBeInClosedState_afterFullLifecycle() {
        assertInstanceOf(ClosedState.class, loan.getState());
    }

    @Test
    void getStatus_shouldReturnClosed() {
        ClosedState state = (ClosedState) loan.getState();
        assertEquals("CLOSED", state.getStatus()); // GAGAL: method belum ada
    }

    @Test
    void shouldThrow_whenApproveCalledOnClosedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.approve());
    }

    @Test
    void shouldThrow_whenRejectCalledOnClosedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.reject());
    }

    @Test
    void shouldThrow_whenCloseCalledAgainOnClosedLoan() {
        assertThrows(InvalidStateTransitionException.class, () -> loan.close());
    }
}