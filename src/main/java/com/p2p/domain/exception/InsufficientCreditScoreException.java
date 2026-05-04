package com.p2p.domain.exception;

public class InsufficientCreditScoreException extends RuntimeException {
    public InsufficientCreditScoreException(int score) {
        super("Credit score " + score + " terlalu rendah, minimal 600");
    }
}