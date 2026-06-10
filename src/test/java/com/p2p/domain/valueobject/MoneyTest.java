package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}