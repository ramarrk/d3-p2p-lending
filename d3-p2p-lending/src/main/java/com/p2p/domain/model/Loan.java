package com.p2p.domain.model;

import com.p2p.domain.state.LoanState; 
import com.p2p.domain.state.PendingState;
import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.ExcessFundingException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    // ==========================================
    // Atribut Domain
    // ==========================================
    private String id;
    private LoanState state;
    private Borrower borrower;
    private Money targetAmount;
    private Money totalFunded = new Money(BigDecimal.ZERO);
    
    private final List<Funding> fundings = new ArrayList<>();
    private final List<FundingObserver> observers = new ArrayList<>();

    // ==========================================
    // Constructors
    // ==========================================
    // Constructor Utama (Bawaan Proyek)
    public Loan(String id) {
        this.id = id;
        this.state = new PendingState(); // Initial State
    }

    // Constructor Pendukung untuk Testing Cucumber (Menampung int targetAmount)
    public Loan(int targetAmountInt) {
        this.targetAmount = new Money(BigDecimal.valueOf(targetAmountInt));
        this.state = new PendingState();
    }

    // ==========================================
    // Modul Core Bisnis & Jembatan Testing (Funding)
    // ==========================================
    
    /**
     * Method Utama: Menerima objek Funding (Digunakan untuk kode aplikasi utama)
     */
    public void addFunding(Funding funding) {
        if (totalFunded.add(funding.getAmount()).isGreaterThan(targetAmount)) {
            throw new ExcessFundingException();
        }
        fundings.add(funding);
        totalFunded = totalFunded.add(funding.getAmount());
        
        if (isFullyFunded()) {
            notifyObservers();
        }
    }

    /**
     * Method Overload: Menerima primitif int (Jembatan khusus Step Definition Cucumber-mu)
     */
    public void addFunding(int amountInt) {
        Money fundingMoney = new Money(BigDecimal.valueOf(amountInt));
        
        // Proteksi guard clause menggunakan exception bawaan kelompok kelompokmu
        if (totalFunded.add(fundingMoney).isGreaterThan(targetAmount)) {
            throw new ExcessFundingException();
        }
        
        this.totalFunded = this.totalFunded.add(fundingMoney);
        
        if (isFullyFunded()) {
            notifyObservers();
        }
    }

    public boolean isFullyFunded() {
        if (totalFunded == null || targetAmount == null) return false;
        return totalFunded.getAmount().compareTo(targetAmount.getAmount()) >= 0;
    }

    private void notifyObservers() {
        observers.forEach(o -> o.onFundingComplete(this));
    }

    // ==========================================
    // Delegasi State Pattern (State Transitions)
    // ==========================================
    public void approve() { state.approve(this); }
    public void reject() { state.reject(this); }
    public void startFunding() { state.startFunding(this); }
    public void disburse() { state.disburse(this); }
    public void startRepayment() { state.startRepayment(this); }
    public void cancel() { state.cancel(this); }
    public void close() { state.close(this); }

    // ==========================================
    // Getter & Setter (Inkapsulasi Data)
    // ==========================================
    public void setState(LoanState state) { this.state = state; }
    public LoanState getState() { return state; }
    public Borrower getBorrower() { return borrower; }
    public String getId() { return id; } 
    public Money getTotalFunded() { return totalFunded; }
    public Money getTargetAmount() { return targetAmount; }
    public List<Funding> getFundings() { return fundings; }
    
    public void setTargetAmount(Money targetAmount) { 
        this.targetAmount = targetAmount; 
    }

    public void addObserver(FundingObserver observer) {
        observers.add(observer);
    }

    // Helper Setter khusus Testing Cucumber untuk memanipulasi dana awal
    public void setTotalFundedAmount(int fundedAmountInt) {
        this.totalFunded = new Money(BigDecimal.valueOf(fundedAmountInt));
    }

    // Helper Getter khusus asersi JUnit (Mengembalikan nilai primitif int)
    public int getTotalFundedAmount() {
        if (this.totalFunded == null || this.totalFunded.getAmount() == null) {
            return 0;
        }
        return this.totalFunded.getAmount().intValue();
    }
}