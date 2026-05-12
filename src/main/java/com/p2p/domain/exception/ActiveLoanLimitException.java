package com.p2p.domain.exception;
public class ActiveLoanLimitException extends RuntimeException {
    public ActiveLoanLimitException() { super("Maksimal 2 pinjaman aktif."); }
}