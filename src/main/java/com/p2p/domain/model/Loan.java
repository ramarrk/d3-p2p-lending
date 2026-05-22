package com.p2p.domain.model;
import com.p2p.domain.state.*;
import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private String id;
    private LoanState state;
    private Borrower borrower;
    private Money targetAmount;
    private Money totalFunded = new Money(BigDecimal.ZERO);
    private List<Funding> fundings = new ArrayList<>();
    private List<FundingObserver> observers = new ArrayList<>();

    public Loan(String id) {
        this.id = id;
        this.state = new PendingState();
    }
    public String getId() { return id; }
    public Borrower getBorrower() { return borrower; }
    public List<Funding> getFundings() { return fundings; }
    public Money getTargetAmount() { return targetAmount; }
    public Money getTotalFunded() { return totalFunded; }
    public LoanState getState() { return state; }

    public void setState(LoanState state) { this.state = state; }
    public void setTargetAmount(Money targetAmount) { this.targetAmount = targetAmount; }

    public void addFunding(Funding funding) { /* abaikan isinya */ }
    public boolean isFullyFunded() { return false; }
    public void addObserver(FundingObserver observer) { observers.add(observer); }
    public void approve() { state.approve(this); }
    public void startFunding() { state.startFunding(this); }
}