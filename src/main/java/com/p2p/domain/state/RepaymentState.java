package com.p2p.domain.state;
import com.p2p.domain.model.Loan;

public class RepaymentState implements LoanState {
    @Override
    public void close(Loan loan) { loan.setState(new ClosedState()); }
}