package com.p2p.domain.strategy;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MurabahahStrategy implements RepaymentCalculationStrategy {
    private final Money margin;

    public MurabahahStrategy(Money margin) {
        this.margin = margin;
    }

    @Override
    public List<Repayment> calculate(String loanId, Money principal, int tenorMonths) {
        BigDecimal totalBayar = principal.getAmount().add(margin.getAmount());
        BigDecimal cicilanPerBulan = totalBayar.divide(
            BigDecimal.valueOf(tenorMonths), 2, RoundingMode.HALF_UP
        );

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