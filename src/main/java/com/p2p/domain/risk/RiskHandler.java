package com.p2p.domain.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

public abstract class RiskHandler {
    protected RiskHandler next;

    public RiskHandler setNext(RiskHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(Borrower borrower, Money amount);
}