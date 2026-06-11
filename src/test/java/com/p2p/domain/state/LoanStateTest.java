package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import com.p2p.domain.model.Loan;
import com.p2p.domain.exception.InvalidStateTransitionException;
import static org.junit.jupiter.api.Assertions.*;

public class LoanStateTest {

    @Test
    public void testDefaultMethodsThrowException() {
        LoanState state = new LoanState() {};
        Loan loan = null;

        assertThrows(InvalidStateTransitionException.class, () -> state.approve(loan));
        assertThrows(InvalidStateTransitionException.class, () -> state.reject(loan));
        assertThrows(InvalidStateTransitionException.class, () -> state.startFunding(loan));
        assertThrows(InvalidStateTransitionException.class, () -> state.startRepayment(loan));
        assertThrows(InvalidStateTransitionException.class, () -> state.close(loan));
        assertThrows(InvalidStateTransitionException.class, () -> state.cancel(loan));
    }
}