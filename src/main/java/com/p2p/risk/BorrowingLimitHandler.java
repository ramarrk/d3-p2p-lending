package com.p2p.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.BorrowingLimitExceededException;

public class BorrowingLimitHandler extends RiskHandler {
    @Override
    protected void validate(Borrower borrower, Money loanAmount) {
        if (loanAmount.isGreaterThan(borrower.getBorrowingLimit())) { // [cite: 102]
            throw new BorrowingLimitExceededException("Jumlah pinjaman melebihi limit borrowing."); // [cite: 88, 104]
        }
    }
}