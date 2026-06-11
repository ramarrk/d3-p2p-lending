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
}