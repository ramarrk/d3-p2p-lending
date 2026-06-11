package com.p2p.domain;

import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FundingTest {

    @Test
    void shouldCreateFundingWithCorrectAttributes() {
        Money amount = new Money(new BigDecimal("5000000"));
        Funding funding = new Funding("F001", "L001", "LN001", amount);

        assertEquals("F001", funding.getId());
        assertEquals("L001", funding.getLoanId());
        assertEquals("LN001", funding.getLenderId());
        assertEquals(amount.getAmount(), funding.getAmount().getAmount());
        assertNotNull(funding.getFundedAt());
    }

    @Test
    void shouldNotAllowNegativeFundingAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Funding("F001", "L001", "LN001",
                        new Money(new BigDecimal("-1000")))
        );
    }
}