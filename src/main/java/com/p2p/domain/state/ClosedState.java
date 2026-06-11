package com.p2p.domain.state;

public class ClosedState extends TerminalLoanState {

    public String getStatus() {
        return "CLOSED";
    }
}