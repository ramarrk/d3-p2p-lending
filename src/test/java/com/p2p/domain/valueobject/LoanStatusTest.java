package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanStatusTest {
    @Test
    public void testLoanStatusValues() {
        LoanStatus[] statuses = LoanStatus.values();
        assertTrue(statuses.length > 0);
        assertNotNull(LoanStatus.valueOf("PENDING"));
        assertNotNull(LoanStatus.valueOf("APPROVED"));
        assertNotNull(LoanStatus.valueOf("REJECTED"));
        assertNotNull(LoanStatus.valueOf("FUNDING"));
        assertNotNull(LoanStatus.valueOf("DISBURSED"));
        assertNotNull(LoanStatus.valueOf("REPAYMENT"));
        assertNotNull(LoanStatus.valueOf("CLOSED"));
        assertNotNull(LoanStatus.valueOf("CANCELLED"));
    }
}
