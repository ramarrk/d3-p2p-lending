package com.p2p.domain;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.observer.BorrowerNotificationObserver;
import com.p2p.domain.observer.DisbursementObserver;
import com.p2p.domain.observer.LenderNotificationObserver;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObserverTest {

    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void lenderNotificationObserver_shouldPrintNotification_whenFundingComplete() {
        Loan loan = mock(Loan.class);
        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));
        when(loan.getId()).thenReturn("L001");
        when(loan.getFundings()).thenReturn(java.util.List.of(funding));
        LenderNotificationObserver observer = new LenderNotificationObserver();

        observer.onFundingComplete(loan);

        String output = outputCaptor.toString();
        assertTrue(output.contains("LN001"), "Notifikasi harus menyebut lender ID: LN001");
        assertTrue(output.contains("L001"), "Notifikasi harus menyebut loan ID: L001");
    }

    @Test
    void borrowerNotificationObserver_shouldPrintNotification_whenFundingComplete() {
        Loan loan = mock(Loan.class);
        Borrower borrower = mock(Borrower.class);
        when(loan.getId()).thenReturn("L001");
        when(loan.getBorrower()).thenReturn(borrower);
        when(borrower.getName()).thenReturn("Andi");
        BorrowerNotificationObserver observer = new BorrowerNotificationObserver();

        observer.onFundingComplete(loan);

        String output = outputCaptor.toString();
        assertTrue(output.contains("Andi"), "Notifikasi harus menyebut nama borrower: Andi");
        assertTrue(output.contains("L001"), "Notifikasi harus menyebut loan ID: L001");
    }

    @Test
    void disbursementObserver_shouldPrintNotification_whenFundingComplete() {
        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn("L001");
        DisbursementObserver observer = new DisbursementObserver();

        observer.onFundingComplete(loan);

        String output = outputCaptor.toString();
        assertTrue(output.contains("L001"), "Log disbursement harus menyebut loan ID: L001");
        assertFalse(output.isEmpty(), "Observer harus menghasilkan output");
    }
}
