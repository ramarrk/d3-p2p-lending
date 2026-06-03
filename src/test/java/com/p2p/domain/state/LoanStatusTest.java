package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanStatusTest {

    @Test
    public void testLoanStatusValues() {
        LoanStatus[] statuses = LoanStatus.values();
        assertTrue(statuses.length > 0);

        String firstName = statuses[0].name();
        assertNotNull(LoanStatus.valueOf(firstName));
    }
}