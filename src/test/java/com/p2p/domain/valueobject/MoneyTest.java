package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MoneyTest {

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(new BigDecimal("-1000"));
        });
    }

    @Test
    void shouldReturnCorrectAmount() {
        Money money = new Money(new BigDecimal("5000"));
        assertEquals(new BigDecimal("5000"), money.getAmount());
    }

    @Test
    void shouldAddTwoMoneyValues() {
        Money a = new Money(new BigDecimal("3000"));
        Money b = new Money(new BigDecimal("2000"));
        Money result = a.add(b);
        assertEquals(new BigDecimal("5000"), result.getAmount());
    }

    @Test
    void shouldSubtractTwoMoneyValues() {
        Money a = new Money(new BigDecimal("5000"));
        Money b = new Money(new BigDecimal("2000"));
        Money result = a.subtract(b);
        assertEquals(new BigDecimal("3000"), result.getAmount());
    }

    @Test
    void shouldReturnTrueWhenAmountIsGreaterThan() {
        Money a = new Money(new BigDecimal("5000"));
        Money b = new Money(new BigDecimal("3000"));
        assertTrue(a.isGreaterThan(b));
    }

    @Test
    void shouldReturnFalseWhenAmountIsNotGreaterThan() {
        Money a = new Money(new BigDecimal("3000"));
        Money b = new Money(new BigDecimal("5000"));
        assertFalse(a.isGreaterThan(b));
    }

    @Test
    void shouldReturnTrueWhenLessThan() {
        Money a = new Money(new BigDecimal("500"));
        Money b = new Money(new BigDecimal("1000"));
        assertTrue(a.isLessThan(b));
    }

    @Test
    void shouldReturnTrueWhenEqual() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("1000"));
        assertTrue(a.isEqualTo(b));
    }
}