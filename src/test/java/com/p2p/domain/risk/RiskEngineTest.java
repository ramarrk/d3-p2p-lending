package com.p2p.domain.risk;

import com.p2p.domain.exception.*;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class RiskEngineTest {
<<<<<<< Updated upstream
    @Test
    void testCreditScoreFail() {
        Borrower b = new Borrower("1", "User", "u@m.com", 500, new Money(new BigDecimal("1000000")));
        RiskHandler handler = new CreditScoreHandler();
        
        assertThrows(InsufficientCreditScoreException.class, () -> 
            handler.handle(b, new Money(new BigDecimal("100000"))));
    }
=======
   @Test
void shouldRejectLowCreditScore() {

    Borrower borrower = new Borrower(
            "1",
            "Test",
            "test@mail.com",
            500,
            new Money(new BigDecimal("1000000"))
    );
>>>>>>> Stashed changes

    @Test
    void testRiskChainSuccess() {
        Borrower b = new Borrower("2", "Safe", "s@m.com", 700, new Money(new BigDecimal("50000000")));
        RiskHandler chain = new CreditScoreHandler();
        chain.setNext(new BorrowingLimitHandler());
        chain.setNext(new ActiveLoanHandler());

<<<<<<< Updated upstream
        assertDoesNotThrow(() -> chain.handle(b, new Money(new BigDecimal("10000000"))));
    }
=======
    assertThrows(
            InsufficientCreditScoreException.class,
            () -> handler.handle(
                    borrower,
                    new Money(new BigDecimal("100000"))
            )
    );
}
>>>>>>> Stashed changes
}