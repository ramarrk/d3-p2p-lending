package com.p2p.domain.model;

public class Funding {
    private final String id;
    private final String loanId;
    private final String lenderId;

    public Funding(String id, String loanId, String lenderId) {
        this.id = id;
        this.loanId = loanId;
        this.lenderId = lenderId;
    }

    public String getId() { return id; }
    public String getLoanId() { return loanId; }
    public String getLenderId() { return lenderId; }
}