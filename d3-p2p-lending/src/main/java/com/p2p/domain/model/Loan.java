package com.p2p.domain.model;
import com.p2p.domain.state.LoanState; 
import com.p2p.domain.state.PendingState;
import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    
    // 1. Konstruktor menerima int, dikonversi ke Value Object Money
    public Loan(int targetAmountInt) {
        this.targetAmount = new Money(BigDecimal.valueOf(targetAmountInt));
        this.state = new PendingState(); // Default awal aman
    }

    // 2. Mengatur nominal total pendanaan langsung dari int menggunakan Value Object Money
    public void setTotalFundedAmount(int fundedAmountInt) {
        this.totalFunded = new Money(BigDecimal.valueOf(fundedAmountInt));
    }

    // 3. Method pembantu untuk dipanggil oleh Cucumber Step: addFunding(Integer amount)
    public void addFunding(int amountInt) {
        Money fundingMoney = new Money(BigDecimal.valueOf(amountInt));
        
    // Menambahkan langsung nominal uang ke totalFunded yang sudah ada
    // Masukkan objek fundingMoney langsung sebagai parameter, tanpa .getAmount()
    this.totalFunded = this.totalFunded.add(fundingMoney);
        
        // Jalankan pengecekan fully funded bawaan kelompokmu
        if (isFullyFunded()) notifyObservers();
    }
    
    // 4. Method pembantu untuk mengambil total dana dalam bentuk primitif int (untuk assertEquals di test)
    public int getTotalFundedAmount() {
        if (this.totalFunded == null || this.totalFunded.getAmount() == null) {
            return 0;
        }
        return this.totalFunded.getAmount().intValue();
    }

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

    public void setTargetAmount(Money targetAmount) {
    this.targetAmount = targetAmount;
    }

}
    
