package com.p2p.domain.state;

public class ClosedState implements LoanState {

    public String getStatus() {
        return "CLOSED";
    }
}