package com.p2p.domain.strategy;

import org.junit.jupiter.api.Test;
import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MurabahahStrategyTest {

    @Test
    public void testCalculateMurabahahRepayment() {
        Money margin = new Money(new BigDecimal("1000000"));
        MurabahahStrategy strategy = new MurabahahStrategy(margin);

        Money principal = new Money(new BigDecimal("10000000"));
        int tenorMonths = 10;

        List<Repayment> jadwal = strategy.calculate("LOAN-003", principal, tenorMonths);

        assertNotNull(jadwal);
        assertEquals(10, jadwal.size());

        Repayment cicilanPertama = jadwal.get(0);
        assertEquals("LOAN-003", cicilanPertama.getLoanId());
        assertNotNull(cicilanPertama.getAmount());
    }
}