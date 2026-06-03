package com.p2p.domain.strategy;

import org.junit.jupiter.api.Test;
import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FloatingRateStrategyTest {

    @Test
    public void testCalculateFloatingRateRepayment() {
        BigDecimal marketRate = new BigDecimal("0.11");
        FloatingRateStrategy strategy = new FloatingRateStrategy(marketRate);

        Money principal = new Money(new BigDecimal("6000000"));
        int tenorMonths = 6;

        List<Repayment> jadwal = strategy.calculate("LOAN-002", principal, tenorMonths);

        assertNotNull(jadwal);
        assertEquals(6, jadwal.size());

        Repayment cicilanPertama = jadwal.get(0);
        assertEquals("LOAN-002", cicilanPertama.getLoanId());
        assertNotNull(cicilanPertama.getAmount());
    }
}