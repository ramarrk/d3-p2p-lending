package com.p2p.domain.model;

import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.LoanStatus;
import com.p2p.domain.valueobject.Money;
import java.util.List;
import java.util.ArrayList;

public class Loan {
    private String id;
    private Borrower borrower;
    private Money targetAmount;
    private Money totalFunded = new Money(java.math.BigDecimal.ZERO);
    private List<Funding> fundings = new ArrayList<>();
    private List<FundingObserver> observers = new ArrayList<>();

    public String getId() { return id; }
    public Borrower getBorrower() { return borrower; }
    public Money getTargetAmount() { return targetAmount; }
    public Money getTotalFunded() { return totalFunded; }
    public List<Funding> getFundings() { return fundings; }

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

    public void addObserver(FundingObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        observers.forEach(o -> o.onFundingComplete(this));
    }
}