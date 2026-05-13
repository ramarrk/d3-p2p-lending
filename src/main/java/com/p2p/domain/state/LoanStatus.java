package com.p2p.domain.state;

public enum LoanStatus {
    PROPOSED,   // Baru diajukan
    VERIFIED,   // Lolos verifikasi risiko (Tugas Irham)
    FUNDED,     // Dana terpenuhi (Tugas Rama)
    DISBURSED,  // Dana dicairkan ke peminjam
    REPAYING,   // Dalam proses cicilan (Tugas Napisz)
    PAID_OFF,   // Lunas
    DEFAULTED   // Gagal bayar
}