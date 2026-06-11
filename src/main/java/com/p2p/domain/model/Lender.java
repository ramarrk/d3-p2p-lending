package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;

public class Lender {
    private String id;
    private String name;
    private String email;
    private Money balance;

    public Lender(String id, String name, String email, Money balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public void deductBalance(Money amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void refundBalance(Money amount) {
        this.balance = this.balance.add(amount);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Money getBalance() { return balance; }
}