package com.p2p.domain.exception;

public class ActiveLoanLimitException extends RuntimeException {
    public ActiveLoanLimitException() {
        super("Borrower sudah memiliki 2 pinjaman aktif");
    }
}