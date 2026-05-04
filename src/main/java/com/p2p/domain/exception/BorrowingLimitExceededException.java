package com.p2p.domain.exception;

public class BorrowingLimitExceededException extends RuntimeException {
    public BorrowingLimitExceededException() {
        super("Jumlah pinjaman melebihi borrowing limit");
    }
}