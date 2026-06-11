package com.p2p.domain.strategy;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FloatingRateStrategy implements RepaymentCalculationStrategy {
    private final BigDecimal marketRate;

    public FloatingRateStrategy(BigDecimal marketRate) {
        this.marketRate = marketRate;
    }

    @Override
    public List<Repayment> calculate(String loanId, Money principal, int tenorMonths) {
        List<Repayment> jadwal = new ArrayList<>();
        LocalDate dueDate = LocalDate.now();

        for (int i = 1; i <= tenorMonths; i++) {
            dueDate = dueDate.plusMonths(1);

//        BigDecimal pokok = principal.getAmount();
//        BigDecimal cicilanPokok = pokok.divide(
//            BigDecimal.valueOf(tenorMonths), 2, RoundingMode.HALF_UP
//        );
//        BigDecimal bunga = sisaPokok.multiply(marketRate)
//                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
//        BigDecimal cicilan = cicilanPokok.add(bunga);
//        sisaPokok = sisaPokok.subtract(cicilanPokok);
//        jadwal.add(new Repayment(
//            UUID.randomUUID().toString(),
//            loanId,
//            dueDate,
//            new Money(cicilan)
//        ));
        }

        return jadwal;
    }
    }