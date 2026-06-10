package com.p2p.domain.valueobject;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }
}