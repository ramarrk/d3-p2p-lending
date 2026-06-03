package com.p2p.domain.valueobject;

import java.math.BigDecimal;

public class InterestRate {
    private final BigDecimal value;

    public InterestRate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0 ||
                value.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("Rate harus antara 0 dan 1");
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.multiply(BigDecimal.valueOf(100)).toPlainString() + "%";
    }
}