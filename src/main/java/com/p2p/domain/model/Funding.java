package com.p2p.domain.model;

public class Funding {
    private final String id;
    private final String loanId;

    public Funding(String id, String loanId) {
        this.id = id;
        this.loanId = loanId;
    }

    public String getId() { return id; }
    public String getLoanId() { return loanId; }
}