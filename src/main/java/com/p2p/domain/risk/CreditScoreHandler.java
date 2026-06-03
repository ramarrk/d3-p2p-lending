package com.p2p.domain.risk;

import com.p2p.domain.exception.InsufficientCreditScoreException;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

public class CreditScoreHandler extends RiskHandler {

    @Override
    public void handle(
            Borrower borrower,
            Money loanAmount) {

        if (borrower.getCreditScore() < 600) {
            throw new InsufficientCreditScoreException();
        }

        if (next != null) {
            next.handle(borrower, loanAmount);
        }
    }
}