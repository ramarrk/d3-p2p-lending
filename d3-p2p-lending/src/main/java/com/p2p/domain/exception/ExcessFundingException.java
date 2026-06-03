package com.p2p.domain.exception;

public class ExcessFundingException extends RuntimeException {
    public ExcessFundingException() {
        super("Total funding melebihi target pinjaman");
    }
}