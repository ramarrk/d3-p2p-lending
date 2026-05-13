package com.p2p.domain.model;

 feature/loan-state
public enum Borrower {

}

import com.p2p.domain.valueobject.Money;

public class Borrower {
    private String id;
    private String name;
    private String email;
    private int creditScore;
    private Money borrowingLimit;
    private int activeLoanCount;

    public Borrower(String id, String name, String email, int creditScore, Money borrowingLimit) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.creditScore = creditScore;
        this.borrowingLimit = borrowingLimit;
        this.activeLoanCount = 0;
    }

    public void incrementActiveLoan() {
        this.activeLoanCount++;
    }

    public void decrementActiveLoan() {
        if (this.activeLoanCount > 0) this.activeLoanCount--;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getCreditScore() { return creditScore; }
    public Money getBorrowingLimit() { return borrowingLimit; }
    public int getActiveLoanCount() { return activeLoanCount; }
}
main
