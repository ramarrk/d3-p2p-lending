package com.p2p.domain.strategy;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.InterestRate;
import com.p2p.domain.valueobject.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixedRateStrategy implements RepaymentCalculationStrategy {
    private final InterestRate annualRate;

    public FixedRateStrategy(InterestRate annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public List<Repayment> calculate(String loanId, Money principal, int tenorMonths) {
        BigDecimal pokok = principal.getAmount();
        BigDecimal rate = annualRate.getValue();
        BigDecimal tenor = BigDecimal.valueOf(tenorMonths);

        BigDecimal totalBunga = pokok.multiply(rate).multiply(tenor)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        BigDecimal totalBayar = pokok.add(totalBunga);
        BigDecimal cicilanPerBulan = totalBayar.divide(tenor, 2, RoundingMode.HALF_UP);

        List<Repayment> jadwal = new ArrayList<>();
        LocalDate dueDate = LocalDate.now(ZoneId.systemDefault());

        for (int i = 1; i <= tenorMonths; i++) {
            dueDate = dueDate.plusMonths(1);
            jadwal.add(new Repayment(
                UUID.randomUUID().toString(),
                loanId,
                dueDate,
                new Money(cicilanPerBulan)
            ));
        }

        return jadwal;
    }
}