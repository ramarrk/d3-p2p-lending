package com.p2p.domain.valueobject;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepaymentStatusTest {
    @Test
    public void testRepaymentStatusValues() {
        RepaymentStatus[] statuses = RepaymentStatus.values();
        assertTrue(statuses.length > 0);
        assertNotNull(RepaymentStatus.valueOf("PENDING"));
        assertNotNull(RepaymentStatus.valueOf("PAID"));
        assertNotNull(RepaymentStatus.valueOf("LATE"));
    }
}
