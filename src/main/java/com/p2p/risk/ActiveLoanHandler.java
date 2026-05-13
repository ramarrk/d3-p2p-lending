package com.p2p.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.ActiveLoanLimitException;

public class ActiveLoanHandler extends RiskHandler {
    @Override
    protected void validate(Borrower borrower, Money loanAmount) {
        if (borrower.getActiveLoanCount() >= 2) { // [cite: 103]
            throw new ActiveLoanLimitException("Borrower sudah memiliki 2 pinjaman aktif."); // [cite: 88, 104]
        }
    }
}
