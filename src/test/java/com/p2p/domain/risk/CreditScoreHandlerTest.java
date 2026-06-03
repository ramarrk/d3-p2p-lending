package com.p2p.domain.risk;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.exception.InsufficientCreditScoreException;
import static org.junit.jupiter.api.Assertions.*;

public class CreditScoreHandlerTest {

    @Test
    public void testHandleWithLowCreditScoreThrowsException() {
        CreditScoreHandler handler = new CreditScoreHandler();
        Borrower mockBorrower = Mockito.mock(Borrower.class);
        Money mockMoney = Mockito.mock(Money.class);

        Mockito.when(mockBorrower.getCreditScore()).thenReturn(550);

        assertThrows(InsufficientCreditScoreException.class, () -> {
            handler.handle(mockBorrower, mockMoney);
        });
    }

    @Test
    public void testHandleWithGoodScoreAndNextHandlerExists() {
        CreditScoreHandler mainHandler = new CreditScoreHandler();
        RiskHandler nextHandler = Mockito.mock(RiskHandler.class);
        mainHandler.setNext(nextHandler);

        Borrower mockBorrower = Mockito.mock(Borrower.class);
        Money mockMoney = Mockito.mock(Money.class);

        Mockito.when(mockBorrower.getCreditScore()).thenReturn(700);

        mainHandler.handle(mockBorrower, mockMoney);

        Mockito.verify(nextHandler, Mockito.times(1)).handle(mockBorrower, mockMoney);
    }

    @Test
    public void testHandleWithGoodScoreAndNextHandlerIsNull() {
        CreditScoreHandler handler = new CreditScoreHandler();
        handler.setNext(null);
        Borrower mockBorrower = Mockito.mock(Borrower.class);
        Money mockMoney = Mockito.mock(Money.class);

        Mockito.when(mockBorrower.getCreditScore()).thenReturn(750);

        assertDoesNotThrow(() -> {
            handler.handle(mockBorrower, mockMoney);
        });
    }
}