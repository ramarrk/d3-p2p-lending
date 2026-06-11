package com.p2p.domain.risk;

import com.p2p.domain.exception.BorrowingLimitExceededException;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

public class BorrowingLimitHandler extends RiskHandler {
    @Override
    public void handle(Borrower borrower, Money loanAmount) {
//        if (loanAmount.isGreaterThan(borrower.getBorrowingLimit())) {
//            throw new BorrowingLimitExceededException();
//        }
        if (next != null) next.handle(borrower, loanAmount);
    }
}