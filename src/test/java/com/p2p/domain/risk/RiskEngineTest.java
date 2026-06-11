package com.p2p.domain.risk;

import com.p2p.domain.exception.*;
import com.p2p.domain.model.Borrower;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class RiskEngineTest {
   @Test
void testCreditScoreFail() {
    Borrower b = new Borrower(
        "1",
        "User",
        "u@m.com",
        500,
        new Money(new BigDecimal("1000000"))
    );

    RiskHandler handler = new CreditScoreHandler();

    assertThrows(
        InsufficientCreditScoreException.class,
        () -> handler.handle(
            b,
            new Money(new BigDecimal("100000"))
        )
    );
}
}