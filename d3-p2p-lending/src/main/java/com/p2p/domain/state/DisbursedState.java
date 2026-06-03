package com.p2p.domain.state;
import com.p2p.domain.model.Loan;

public class DisbursedState implements LoanState {
    @Override
    public void startRepayment(Loan loan) { loan.setState(new RepaymentState()); }
}