package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepaymentStatusTest {

    @Test
    public void testRepaymentStatusValues() {
        assertNotNull(RepaymentStatus.valueOf("PENDING"));
        assertNotNull(RepaymentStatus.valueOf("PAID"));
        assertNotNull(RepaymentStatus.valueOf("LATE"));

        assertEquals(3, RepaymentStatus.values().length);
    }
}