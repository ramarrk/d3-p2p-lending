package com.p2p.domain.risk;
 
import com.p2p.domain.exception.*;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
 
class RiskEngineTest {
 
    private RiskEngine riskEngine;
 
    @BeforeEach
    void setUp() {
        riskEngine = new RiskEngine();
    }
 
    // RED Task 1: Chain menolak borrower dengan credit score < 600
    @Test
    void testValidateShouldThrowInsufficientCreditScoreWhenScoreIsTooLow() {
        Borrower borrower = new Borrower("1", "User", "u@mail.com", 500,
                new Money(new BigDecimal("10000000")));
 
        assertThrows(InsufficientCreditScoreException.class, () ->
                riskEngine.validate(borrower, new Money(new BigDecimal("1000000")))
        );
    }
 
    // RED Task 2: Chain menolak borrower jika jumlah pinjaman melebihi limit
    @Test
    void testValidateShouldThrowBorrowingLimitExceededWhenAmountExceedsLimit() {
        Borrower borrower = new Borrower("2", "User", "u@mail.com", 700,
                new Money(new BigDecimal("5000000"))); // limit Rp 5jt
 
        assertThrows(BorrowingLimitExceededException.class, () ->
                riskEngine.validate(borrower, new Money(new BigDecimal("10000000"))) // minta Rp 10jt
        );
    }
 
    // RED Task 3: Chain menolak borrower jika sudah punya 2 pinjaman aktif
    @Test
    void testValidateShouldThrowActiveLoanLimitWhenBorrowerHasTwoActiveLoans() {
        Borrower borrower = new Borrower("3", "User", "u@mail.com", 700,
                new Money(new BigDecimal("50000000")));
        borrower.incrementActiveLoan();
        borrower.incrementActiveLoan(); // 2 pinjaman aktif
 
        assertThrows(ActiveLoanLimitException.class, () ->
                riskEngine.validate(borrower, new Money(new BigDecimal("1000000")))
        );
    }
 
    // GREEN: Chain berhasil jika semua validasi lolos
    @Test
    void testValidateShouldPassWhenAllConditionsAreMet() {
        Borrower borrower = new Borrower("4", "Safe User", "safe@mail.com", 700,
                new Money(new BigDecimal("50000000"))); // limit Rp 50jt, 0 pinjaman aktif
 
        assertDoesNotThrow(() ->
                riskEngine.validate(borrower, new Money(new BigDecimal("10000000")))
        );
    }
 
    // GREEN: Credit score gagal sebelum cek limit — urutan chain terjaga
    @Test
    void testValidateShouldFailOnCreditScoreBeforeBorrowingLimit() {
        // Skor rendah DAN amount melebihi limit — harus gagal di credit score dulu
        Borrower borrower = new Borrower("5", "Bad User", "bad@mail.com", 400,
                new Money(new BigDecimal("1000000")));
 
        // Exception yang muncul harus InsufficientCreditScore, bukan BorrowingLimitExceeded
        assertThrows(InsufficientCreditScoreException.class, () ->
                riskEngine.validate(borrower, new Money(new BigDecimal("5000000")))
        );
    }
 
    // GREEN: Borrower dengan 1 pinjaman aktif masih boleh meminjam
    @Test
    void testValidateShouldPassWhenBorrowerHasOneActiveLoan() {
        Borrower borrower = new Borrower("6", "User", "u@mail.com", 750,
                new Money(new BigDecimal("50000000")));
        borrower.incrementActiveLoan(); // hanya 1 pinjaman aktif
 
        assertDoesNotThrow(() ->
                riskEngine.validate(borrower, new Money(new BigDecimal("5000000")))
        );
    }
}
 