package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Loan;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DisbursedStateTest {

    @Test
    public void testStartRepaymentChangesState() {
        DisbursedState state = new DisbursedState();
        Loan mockLoan = Mockito.mock(Loan.class);

        state.startRepayment(mockLoan);

        verify(mockLoan, times(1)).setState(any(RepaymentState.class));
        assertNotNull(state);
    }
}