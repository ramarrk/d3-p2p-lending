package com.p2p.domain.state;

public class RejectedState extends TerminalLoanState {

    public String getStatus() {
        return "REJECTED";
    }
}