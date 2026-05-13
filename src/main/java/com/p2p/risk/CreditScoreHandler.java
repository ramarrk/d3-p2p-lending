package com.p2p.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.InsufficientCreditScoreException;

public class CreditScoreHandler extends RiskHandler {
    @Override
    protected void validate(Borrower borrower, Money loanAmount) {
        if (borrower.getCreditScore() < 600) { // [cite: 101]
            throw new InsufficientCreditScoreException("Credit score borrower di bawah 600."); // [cite: 87, 104]
        }
    }
}
