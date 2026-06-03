package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithPositiveAmount() {
        Money money = new Money(new BigDecimal("1000"));
        assertEquals(new BigDecimal("1000"), money.getAmount());
    }

    @Test
    void shouldCreateMoneyWithZeroAmount() {
        Money money = new Money(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, money.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenAmountNegative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Money(new BigDecimal("-1"))
        );
    }

    @Test
    void shouldAddMoneyCorrectly() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("500"));
        Money result = a.add(b);
        assertEquals(new BigDecimal("1500"), result.getAmount());
    }

    @Test
    void shouldSubtractMoneyCorrectly() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("500"));
        Money result = a.subtract(b);
        assertEquals(new BigDecimal("500"), result.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenSubtractionResultsInNegative() {
        Money a = new Money(new BigDecimal("500"));
        Money b = new Money(new BigDecimal("1000"));
        assertThrows(
                IllegalArgumentException.class,
                () -> a.subtract(b)
        );
    }

    @Test
    void shouldReturnTrueWhenGreaterThan() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("500"));
        assertTrue(a.isGreaterThan(b));
    }

    @Test
    void shouldReturnFalseWhenGreaterThan() {
        Money a = new Money(new BigDecimal("500"));
        Money b = new Money(new BigDecimal("1000"));
        assertFalse(a.isGreaterThan(b));
    }

    @Test
    void shouldReturnTrueWhenLessThan() {
        Money a = new Money(new BigDecimal("500"));
        Money b = new Money(new BigDecimal("1000"));
        assertTrue(a.isLessThan(b));
    }

    @Test
    void shouldReturnFalseWhenLessThan() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("500"));
        assertFalse(a.isLessThan(b));
    }

    @Test
    void shouldReturnTrueWhenEqual() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("1000"));
        assertTrue(a.isEqualTo(b));
    }

    @Test
    void shouldReturnFalseWhenNotEqual() {
        Money a = new Money(new BigDecimal("1000"));
        Money b = new Money(new BigDecimal("500"));
        assertFalse(a.isEqualTo(b));
    }

    @Test
    void shouldReturnFormattedString() {
        Money money = new Money(new BigDecimal("1000"));
        assertEquals("Rp 1000", money.toString());
    }
}