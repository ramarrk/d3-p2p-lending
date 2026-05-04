package com.p2p.domain.exception;

import com.p2p.domain.valueobject.LoanStatus;

public class InvalidStateTransitionException extends RuntimeException {
    public InvalidStateTransitionException(LoanStatus from, LoanStatus to) {
        super("Tidak bisa transisi dari " + from + " ke " + to);
    }
}