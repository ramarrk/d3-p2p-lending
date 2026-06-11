package com.p2p.domain.state;

import com.p2p.domain.model.Loan;

public class FundingState implements LoanState {
    @Override
    public void disburse(Loan loan) {
        loan.setState(new DisbursedState());
    }

    @Override
    public void cancel(Loan loan) {
        loan.setState(new CancelledState());
    }
}