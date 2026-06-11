package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;

public class Funding {
    private final String id;
    private final String loanId;
    private final String lenderId;
    private final Money amount;

    public Funding(String id, String loanId, String lenderId, Money amount) {
        this.id = id;
        this.loanId = loanId;
        this.lenderId = lenderId;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getLoanId() { return loanId; }
    public String getLenderId() { return lenderId; }
    public Money getAmount() { return amount; }
}