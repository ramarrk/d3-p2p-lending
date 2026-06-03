package com.p2p.domain.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Repayment;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.valueobject.InterestRate;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FixedRateStrategyTest {

    @Test
    public void testCalculateFixedRateRepayment() {
        InterestRate mockRate = Mockito.mock(InterestRate.class);
        when(mockRate.getValue()).thenReturn(new BigDecimal("0.12"));

        FixedRateStrategy strategy = new FixedRateStrategy(mockRate);
        Money principal = new Money(new BigDecimal("12000000"));
        int tenorMonths = 12;

        List<Repayment> jadwal = strategy.calculate("LOAN-001", principal, tenorMonths);

        assertNotNull(jadwal);
        assertEquals(12, jadwal.size());

        Repayment cicilanPertama = jadwal.get(0);
        assertEquals("LOAN-001", cicilanPertama.getLoanId());
        assertNotNull(cicilanPertama.getAmount());
    }
}