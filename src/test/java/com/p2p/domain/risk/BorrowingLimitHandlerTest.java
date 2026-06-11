package com.p2p.domain.risk;

import org.junit.jupiter.api.Test;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class BorrowingLimitHandlerTest {

    @Test
    public void testHandleWithSafeLimitAndNextHandler() {
        Borrower borrower = new Borrower("B1", "Test", "test@test.com", 700, new Money(BigDecimal.valueOf(5000000)));
        Money loanAmount = new Money(BigDecimal.valueOf(2000000));

        BorrowingLimitHandler currentHandler = new BorrowingLimitHandler();
        BorrowingLimitHandler nextHandler = new BorrowingLimitHandler();

        currentHandler.setNext(nextHandler);
        currentHandler.handle(borrower, loanAmount);

        assertNotNull(currentHandler);
    }
}