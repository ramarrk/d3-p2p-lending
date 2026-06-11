package com.p2p.domain.strategy;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;
import java.util.List;

public interface RepaymentCalculationStrategy {
    List<Repayment> calculate(String loanId, Money principal, int tenorMonths);
}