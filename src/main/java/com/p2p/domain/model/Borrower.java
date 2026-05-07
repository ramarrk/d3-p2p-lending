package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.InsufficientCreditScoreException;
import com.p2p.domain.exception.BorrowingLimitExceededException;
import com.p2p.domain.exception.ActiveLoanLimitException;

public class Borrower {
    private String id; // UUID 
    private String name; // Nama lengkap 
    private String email; // Email unik 
    private int creditScore; // Skor kredit (0-1000) 
    private Money borrowingLimit; // Batas maksimal pinjaman 
    private int activeLoanCount; // Jumlah pinjaman aktif saat ini 

    // Constructor, Getters, and Setters...

    // Logika Bisnis [cite: 26]
    public void validateEligibility(Money amount) { // [cite: 27]
        if (this.creditScore < 600) {
            throw new InsufficientCreditScoreException("Credit score < 600"); // [cite: 87]
        }
        if (amount.isGreaterThan(this.borrowingLimit)) {
            throw new BorrowingLimitExceededException("Jumlah pinjaman > borrowingLimit"); // [cite: 88]
        }
        if (this.activeLoanCount >= 2) {
            throw new ActiveLoanLimitException("Borrower sudah punya 2 pinjaman aktif"); // [cite: 88]
        }
    }

    public void incrementActiveLoan() {
        // Dipanggil saat loan APPROVED [cite: 28]
        this.activeLoanCount++;
    }

    public void decrementActiveLoan() {
        // Dipanggil saat loan CLOSED/CANCELLED [cite: 29]
        if (this.activeLoanCount > 0) {
            this.activeLoanCount--;
        }
    }
}
