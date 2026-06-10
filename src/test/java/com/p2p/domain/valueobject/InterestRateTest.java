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

    @Test
    public void testInterestRateInvalidGreaterThanOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new InterestRate(new BigDecimal("1.01"));
        });
    }

    @Test
    public void testInterestRateValidAndBoundary() {
        BigDecimal validVal = new BigDecimal("0.15");
        InterestRate rate = new InterestRate(validVal);
        assertEquals(validVal, rate.getValue());
        assertNotNull(new InterestRate(BigDecimal.ZERO));
        assertNotNull(new InterestRate(BigDecimal.ONE));
    }
    bash
}
