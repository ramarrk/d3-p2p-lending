package com.p2p.domain.model;
import com.p2p.domain.state.LoanState; 
import com.p2p.domain.state.PendingState;
import com.p2p.domain.model.Funding;
import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.p2p.domain.model.Borrower;

public class Loan {
    private String id;
    private Borrower borrower;
    private LoanState state;

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

    public String getId() {
        return id;
    }
    public Borrower getBorrower() { return borrower; }
    public void setBorrower(Borrower borrower) { this.borrower = borrower; }
    private Money targetAmount;
    private Money totalFunded = new Money(BigDecimal.ZERO);
    private List<Funding> fundings = new ArrayList<>();
    private List<FundingObserver> observers = new ArrayList<>();

    // Delegasi method ke objek state
    public void approve() { state.approve(this); }
    public void reject() { state.reject(this); }
    public void startFunding() { state.startFunding(this); }
    public void disburse() { state.disburse(this); }
    public void startRepayment() { state.startRepayment(this); }
    public void cancel() { state.cancel(this); }
    public void close() { state.close(this); }
    public void addObserver(FundingObserver observer) {
        observers.add(observer);
    }
    public void addFunding(Funding funding) {
        if (totalFunded.add(funding.getAmount()).isGreaterThan(targetAmount))
            throw new com.p2p.domain.exception.ExcessFundingException();
        fundings.add(funding);
        totalFunded = totalFunded.add(funding.getAmount());
        if (isFullyFunded()) notifyObservers();
    }

    public boolean isFullyFunded() {
        return totalFunded.getAmount().compareTo(targetAmount.getAmount()) >= 0;
    }

    public Money getTotalFunded() { return totalFunded; }
    public Money getTargetAmount() { return targetAmount; }
    public List<Funding> getFundings() { return fundings; }

    private void notifyObservers() {
        observers.forEach(o -> o.onFundingComplete(this));
    }
}
    
