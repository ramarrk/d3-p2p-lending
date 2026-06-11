package com.p2p.domain.state;

import com.p2p.domain.exception.InvalidStateTransitionException;
import com.p2p.domain.model.Loan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RejectedStateTest {

    @Test
    void shouldTransitionToRejected_whenRejectedFromPending() {
        Loan loan = new Loan("L-REJ-001");
        loan.reject(); // sengaja error dulu
        assertInstanceOf(RejectedState.class, loan.getState());
    }

    @Test
    void shouldThrow_whenAnyActionCalledOnRejectedLoan() {
        Loan loan = new Loan("L-REJ-002");
        loan.reject(); // sengaja error dulu
        assertThrows(InvalidStateTransitionException.class, () -> loan.approve());
    }
}