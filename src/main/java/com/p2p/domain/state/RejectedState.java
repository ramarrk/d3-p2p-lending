package com.p2p.domain.state;

public class RejectedState implements LoanState {

    public String getStatus() {
        return "REJECTED";
    }
}