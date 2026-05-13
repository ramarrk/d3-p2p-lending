package com.p2p.domain.exception;

public class InsufficientCreditScoreException extends RuntimeException {
    public InsufficientCreditScoreException() {
        super("Credit score terlalu rendah, minimal 600");
    }
    public InsufficientCreditScoreException(String message) {
        super(message);
    }
}