package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Loan;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PendingStateTest {

    @Test
    public void testApproveChangesStateToApproved() {
        PendingState state = new PendingState();
        Loan mockLoan = Mockito.mock(Loan.class);

        state.approve(mockLoan);

        verify(mockLoan, times(1)).setState(any(ApprovedState.class));
        assertNotNull(state);
    }

    @Test
    public void testRejectChangesStateToRejected() {
        PendingState state = new PendingState();
        Loan mockLoan = Mockito.mock(Loan.class);

        state.reject(mockLoan);

        verify(mockLoan, times(1)).setState(any(RejectedState.class));
        assertNotNull(state);
    }
}