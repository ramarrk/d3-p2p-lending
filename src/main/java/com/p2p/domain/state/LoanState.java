package com.p2p.domain.state;

import com.p2p.domain.model.Loan;
import com.p2p.domain.valueobject.LoanStatus;

public interface LoanState {
    void approve(Loan loan);
    void reject(Loan loan);
    void startFunding(Loan loan);
    void cancel(Loan loan);
    void disburse(Loan loan);
    void startRepayment(Loan loan);
    void close(Loan loan);
    LoanStatus getStatus();
}