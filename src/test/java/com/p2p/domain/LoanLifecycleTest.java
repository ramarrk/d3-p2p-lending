package com.p2p.domain;

import com.p2p.domain.model.Loan;
import com.p2p.domain.state.PendingState;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class LoanLifecycleTest {
    @Test
    void shouldStartInPendingState() {
        Loan loan = new Loan("L001");
        loan.setTargetAmount(new Money(new BigDecimal("10000000")));
        assertInstanceOf(PendingState.class, loan.getState());
    }
}