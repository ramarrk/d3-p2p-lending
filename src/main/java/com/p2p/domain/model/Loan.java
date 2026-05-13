package com.p2p.domain.model;
import com.p2p.domain.state.LoanState; 
import com.p2p.domain.state.PendingState; 

public class Loan {
    private String id;
    private LoanState state;
    private Borrower borrower;

    public Loan(String id) {
        this.id = id;
        this.state = new PendingState(); // Initial State
    }

    public void setState(LoanState state) {
        this.state = state;
    }

    public LoanState getState() {
        return state;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public String getId() {
        return id;
    }

    // Delegasi method ke objek state
    public void approve() { state.approve(this); }
    public void reject() { state.reject(this); }
    public void startFunding() { state.startFunding(this); }
    public void disburse() { state.disburse(this); }
    public void startRepayment() { state.startRepayment(this); }
    public void cancel() { state.cancel(this); }
    public void close() { state.close(this); }
}
    
