package com.p2p.domain.strategy;

import com.p2p.domain.valueobject.InterestRate;
import com.p2p.domain.valueobject.LoanScheme;
import com.p2p.domain.valueobject.Money;

import java.math.BigDecimal;

public class RepaymentStrategyFactory {

    public static RepaymentCalculationStrategy create(
            LoanScheme scheme,
            InterestRate rate,
            Money murabahahMargin,
            BigDecimal marketRate) {

        switch (scheme) {
            case FIXED:
                return new FixedRateStrategy(rate);
            case FLOATING:
                return new FloatingRateStrategy(marketRate);
            case MURABAHAH:
                return new MurabahahStrategy(murabahahMargin);
            default:
                throw new IllegalArgumentException("Scheme tidak dikenali: " + scheme);
        }
    }
}