package com.p2p.domain.state;

import com.p2p.domain.model.Loan;
import com.p2p.domain.exception.InvalidStateTransitionException;

public interface LoanState {
    default void approve(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat melakukan approve pada state " + this.getClass().getSimpleName());
    }
    default void reject(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat melakukan reject pada state " + this.getClass().getSimpleName());
    }
    default void startFunding(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat memulai funding pada state " + this.getClass().getSimpleName());
    }
    default void disburse(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat mencairkan dana pada state " + this.getClass().getSimpleName());
    }
    default void startRepayment(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat memulai repayment pada state " + this.getClass().getSimpleName());
    }
    default void close(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat menutup pinjaman pada state " + this.getClass().getSimpleName());
    }
    default void cancel(Loan loan) {
        throw new InvalidStateTransitionException("Transisi tidak valid: Tidak dapat membatalkan pinjaman pada state " + this.getClass().getSimpleName());
    }
    
}