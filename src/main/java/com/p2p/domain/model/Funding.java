package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;
import java.time.LocalDateTime;

public class Funding {
    private final String id;
    private final String loanId;
    private final String lenderId;
    private final Money amount;
    private final LocalDateTime fundedAt;

    public String getId() { return id; }
    public String getLoanId() { return loanId; }
    public String getLenderId() { return lenderId; }
    public Money getAmount() { return amount; }
    public LocalDateTime getFundedAt() { return fundedAt; }
    public Funding(String id, String loanId, String lenderId, Money amount) {
        if (amount == null) throw new IllegalArgumentException("Amount tidak boleh null");
        this.id = id;
        this.loanId = loanId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.fundedAt = LocalDateTime.now();
    }
}