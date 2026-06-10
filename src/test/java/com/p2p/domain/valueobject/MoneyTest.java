package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}