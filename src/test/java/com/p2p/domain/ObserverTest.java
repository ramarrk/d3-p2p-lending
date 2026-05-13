package com.p2p.domain;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.observer.BorrowerNotificationObserver;
import com.p2p.domain.observer.DisbursementObserver;
import com.p2p.domain.observer.LenderNotificationObserver;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObserverTest {

    @Test
    void lenderNotificationObserver_shouldPrintNotification_whenFundingComplete() {
        // ARRANGE
        Loan loan = mock(Loan.class);
        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));

        when(loan.getId()).thenReturn("L001");
        when(loan.getFundings()).thenReturn(java.util.List.of(funding));

        LenderNotificationObserver observer = new LenderNotificationObserver();

        // ACT & ASSERT — pastikan tidak throw exception
        assertDoesNotThrow(() -> observer.onFundingComplete(loan));
    }

    @Test
    void borrowerNotificationObserver_shouldPrintNotification_whenFundingComplete() {
        // ARRANGE
        Loan loan = mock(Loan.class);
        com.p2p.domain.model.Borrower borrower = mock(com.p2p.domain.model.Borrower.class);

        when(loan.getId()).thenReturn("L001");
        when(loan.getBorrower()).thenReturn(borrower);
        when(borrower.getName()).thenReturn("Andi");

        BorrowerNotificationObserver observer = new BorrowerNotificationObserver();

        // ACT & ASSERT
        assertDoesNotThrow(() -> observer.onFundingComplete(loan));
    }

    @Test
    void disbursementObserver_shouldPrintNotification_whenFundingComplete() {
        // ARRANGE
        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn("L001");

        DisbursementObserver observer = new DisbursementObserver();

        // ACT & ASSERT
        assertDoesNotThrow(() -> observer.onFundingComplete(loan));
    }
}