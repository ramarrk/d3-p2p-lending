package com.p2p.domain.exception;
public class InsufficientCreditScoreException extends RuntimeException {
    public InsufficientCreditScoreException() { super("Credit score minimal 600."); }
}