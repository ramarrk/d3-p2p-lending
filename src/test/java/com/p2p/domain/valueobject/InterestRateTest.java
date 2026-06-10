package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterestRateTest {
    @Test
    public void testInterestRateInvalidLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new InterestRate(new BigDecimal("-0.01"));
        });
    }
}
