package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanStatusTest {
    @Test
    public void testLoanStatusValues() {
        assertNotNull(LoanStatus.valueOf("PENDING"));
        assertNotNull(LoanStatus.valueOf("CANCELLED"));
        assertEquals(8, LoanStatus.values().length);
    }
}