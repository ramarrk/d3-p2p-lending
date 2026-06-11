package com.p2p.domain;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.valueobject.RepaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RepaymentTest {

    // ─── Helper ───────────────────────────────────────────────────────────────
    private Repayment buatRepayment() {
        return new Repayment(
                "RPY-001",
                "L001",
                LocalDate.of(2026, 6, 1),
                new Money(new BigDecimal("500000"))
        );
    }

    // ─── 1. Status awal harus PENDING ─────────────────────────────────────────
    @Test
    void statusAwalHarusPending() {
        Repayment repayment = buatRepayment();

        assertEquals(RepaymentStatus.PENDING, repayment.getStatus());
    }

    // ─── 2. Atribut tersimpan dengan benar ────────────────────────────────────
    @Test
    void atributTersimpanDenganBenar() {
        Repayment repayment = buatRepayment();

        assertEquals("RPY-001", repayment.getId());
        assertEquals("L001", repayment.getLoanId());
        assertEquals(LocalDate.of(2026, 6, 1), repayment.getDueDate());
        assertEquals(new BigDecimal("500000"), repayment.getAmount().getAmount());
    }

    // ─── 3. markAsPaid mengubah status menjadi PAID ───────────────────────────
    @Test
    void markAsPaidMengubahStatusMenjadiPaid() {
        Repayment repayment = buatRepayment();

        repayment.markAsPaid();

        assertEquals(RepaymentStatus.PAID, repayment.getStatus());
        assertNotNull(repayment.getPaidAt());
    }

    // ─── 4. Pembayaran tepat waktu tidak kena denda ───────────────────────────
    @Test
    void pembayaranTepat_tidakKenaDenda() {
        Repayment repayment = buatRepayment();
        LocalDate bayarTepat = LocalDate.of(2026, 6, 1); // sama dengan dueDate

        repayment.calculatePenalty(bayarTepat);

        assertEquals(BigDecimal.ZERO, repayment.getPenalty().getAmount());
        // Status tidak berubah ke LATE
        assertNotEquals(RepaymentStatus.LATE, repayment.getStatus());
    }

    // ─── 5. Pembayaran terlambat kena denda dan status LATE ──────────────────
    @Test
    void pembayaranTerlambat_kenaDendaDanStatusLate() {
        Repayment repayment = buatRepayment();
        LocalDate bayarTerlambat = LocalDate.of(2026, 6, 4); // 3 hari terlambat

        repayment.calculatePenalty(bayarTerlambat);

        // Denda = 3 hari × 50.000 = 150.000
        assertEquals(new BigDecimal("150000"), repayment.getPenalty().getAmount());
        assertEquals(RepaymentStatus.LATE, repayment.getStatus());
    }

    // ─── 6. Denda dihitung proporsional sesuai jumlah hari ───────────────────
    @Test
    void dendaDihitungProporsional() {
        Repayment repayment = buatRepayment();
        LocalDate bayarTerlambat = LocalDate.of(2026, 6, 11); // 10 hari terlambat

        repayment.calculatePenalty(bayarTerlambat);

        // Denda = 10 hari × 50.000 = 500.000
        assertEquals(new BigDecimal("500000"), repayment.getPenalty().getAmount());
    }

    // ─── 7. Penalty awal adalah nol ──────────────────────────────────────────
    @Test
    void penaltyAwalAdaNol() {
        Repayment repayment = buatRepayment();

        assertEquals(BigDecimal.ZERO, repayment.getPenalty().getAmount());
    }

    // ─── 8. paidAt null sebelum markAsPaid ───────────────────────────────────
    @Test
    void paidAtNullSebelumDibayar() {
        Repayment repayment = buatRepayment();

        assertNull(repayment.getPaidAt());
    }
}