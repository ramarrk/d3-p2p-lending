package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void testInterestRateToString() {
        InterestRate rate = new InterestRate(new BigDecimal("0.15"));
        assertEquals("15.00%", rate.toString());
    }
}
