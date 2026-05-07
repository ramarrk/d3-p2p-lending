package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;

public class Lender {
    private String id; // UUID [cite: 33]
    private String name; // Nama lengkap [cite: 34]
    private String email; // Email unik [cite: 35]
    private Money balance; // Saldo tersedia [cite: 36]

    // Constructor, Getters, and Setters...

    // Logika Bisnis [cite: 37]
    public void deductBalance(Money amount) {
        // Saat lender mendanai [cite: 38]
        // Asumsi Money memiliki method subtract() yang valid
        this.balance = this.balance.subtract(amount); 
    }

    public void refundBalance(Money amount) {
        // Saat pinjaman CANCELLED [cite: 39]
        this.balance = this.balance.add(amount);
    }
}