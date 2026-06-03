package com.p2p.domain.state;

import com.p2p.domain.model.Loan; // Import Loan karena dipakai di parameter

public class PendingState implements LoanState {
    @Override
    public void approve(Loan loan) {
        loan.setState(new ApprovedState());
    }

    @Override
    public void reject(Loan loan) {
        loan.setState(new RejectedState());
    }
}