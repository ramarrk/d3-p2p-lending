package com.p2p.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

public abstract class RiskHandler {
    private RiskHandler nextHandler;

    public void setNext(RiskHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void check(Borrower borrower, Money loanAmount) {
        // Lakukan validasi di subclass
        validate(borrower, loanAmount);
        
        // Teruskan ke handler berikutnya jika ada
        if (nextHandler != null) {
            nextHandler.check(borrower, loanAmount);
        }
    }

    protected abstract void validate(Borrower borrower, Money loanAmount);
}
