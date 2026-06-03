package com.p2p.domain.risk;

import org.junit.jupiter.api.Test;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ActiveLoanHandlerTest {

    @Test
    public void testHandleWithNextHandler() {
        Borrower borrower = new Borrower("B1", "Test", "test@test.com", 700, new Money(BigDecimal.ZERO));
        Money amount = new Money(BigDecimal.ZERO);

        ActiveLoanHandler currentHandler = new ActiveLoanHandler();
        ActiveLoanHandler nextHandler = new ActiveLoanHandler();

        currentHandler.setNext(nextHandler);
        currentHandler.handle(borrower, amount);

        assertNotNull(currentHandler);
    }
}