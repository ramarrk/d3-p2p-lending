package com.p2p.domain.valueobject;

import java.math.BigDecimal;

public class InterestRate {
    private final BigDecimal value;

    public InterestRate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Rate tidak boleh kurang dari 0");
        this.value = value;
    }

}