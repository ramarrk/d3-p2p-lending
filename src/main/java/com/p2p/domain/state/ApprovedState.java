package com.p2p.domain.state;
import com.p2p.domain.model.Loan;

public class ApprovedState implements LoanState {
    @Override
    public void startFunding(Loan loan) {
        loan.setState(new FundingState());
    }
}