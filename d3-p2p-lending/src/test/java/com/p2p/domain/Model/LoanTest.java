package com.p2p.domain.Model;

import com.p2p.domain.state.*;
import com.p2p.domain.exception.InvalidStateTransitionException;
import com.p2p.domain.model.Loan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private Loan loan;

    @BeforeEach
    void setUp() {
        // Inisialisasi pinjaman baru sebelum setiap test dijalankan
        loan = new Loan("LOAN-001");
    }

    @Test
    void testInitialStateIsPending() {
        // Mengecek apakah status awal adalah Pending
        assertTrue(loan.getState() instanceof PendingState, "Status awal harus PENDING");
    }

    @Test
    void testPendingToApproved() {
        // Mengetes transisi dari Pending ke Approved
        loan.approve();
        assertTrue(loan.getState() instanceof ApprovedState, "Status harus berubah menjadi APPROVED");
    }

    @Test
    void testFullCycle() {
        // Mengetes siklus hidup lengkap pinjaman
        loan.approve();
        loan.startFunding();
        loan.disburse();
        loan.startRepayment();
        loan.close();
        assertTrue(loan.getState() instanceof ClosedState, "Status akhir harus CLOSED");
    }

    @Test
    void testInvalidTransition() {
        // Mengetes apakah sistem melarang transisi yang tidak masuk akal
        // Contoh: Dari Pending langsung loncat ke Disburse (Cair)
        assertThrows(InvalidStateTransitionException.class, () -> {
            loan.disburse();
        }, "Harusnya error karena tidak bisa mencairkan dana saat masih PENDING");
    }

    @Test
    void testCancelFromFunding() {
        // Mengetes pembatalan saat masa pendanaan
        loan.approve();
        loan.startFunding();
        loan.cancel();
        assertTrue(loan.getState() instanceof CancelledState, "Status harus CANCELLED jika dibatalkan saat pendanaan");
    }
}
