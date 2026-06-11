package com.p2p.domain.risk;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;

/**
 * RiskEngine merangkai ketiga handler (Chain of Responsibility):
 * CreditScoreHandler → BorrowingLimitHandler → ActiveLoanHandler
 *
 * Jika salah satu gagal, exception langsung dilempar dan chain berhenti.
 */
public class RiskEngine {

    private final RiskHandler chain;

    public RiskEngine() {
        // Rangkai chain: credit score → borrowing limit → active loan
        CreditScoreHandler creditScoreHandler = new CreditScoreHandler();
        BorrowingLimitHandler borrowingLimitHandler = new BorrowingLimitHandler();
        ActiveLoanHandler activeLoanHandler = new ActiveLoanHandler();

        creditScoreHandler.setNext(borrowingLimitHandler);
        borrowingLimitHandler.setNext(activeLoanHandler);

        this.chain = creditScoreHandler;
    }

    /**
     * Jalankan semua validasi kelayakan borrower.
     * Melempar exception pertama yang gagal.
     *
     * @param borrower   data borrower yang akan divalidasi
     * @param loanAmount jumlah pinjaman yang diminta
     */
    public void validate(Borrower borrower, Money loanAmount) {
        chain.handle(borrower, loanAmount);
    }
}
