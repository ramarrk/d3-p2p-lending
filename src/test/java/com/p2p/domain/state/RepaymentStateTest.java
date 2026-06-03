package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Loan;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RepaymentStateTest {

    @Test
    public void testCloseChangesStateToClosed() {
        RepaymentState state = new RepaymentState();
        Loan mockLoan = Mockito.mock(Loan.class);

        state.close(mockLoan);

        verify(mockLoan, times(1)).setState(any(ClosedState.class));
        assertNotNull(state);
    }
}