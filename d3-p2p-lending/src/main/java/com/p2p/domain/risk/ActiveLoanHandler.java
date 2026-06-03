package com.p2p.domain.risk; // Pastikan ini benar

import com.p2p.domain.exception.ActiveLoanLimitException; // Import dari package exception
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

public class ActiveLoanHandler extends RiskHandler {
    @Override
    public void handle(Borrower borrower, Money loanAmount) {
        if (borrower.getActiveLoanCount() >= 2) {
            throw new ActiveLoanLimitException();
        }
        if (next != null) next.handle(borrower, loanAmount);
    }
}