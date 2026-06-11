package com.p2p.domain;

import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FundingTest {
    @Test
    void shouldReturnCorrectId() {
        Funding funding = new Funding("F001", "L001");
        assertEquals("F001", funding.getId());
    }

    @Test
    void shouldReturnCorrectLoanId() {
        Funding funding = new Funding("F001", "L001");
        assertEquals("L001", funding.getLoanId());
    }

    @Test
    void shouldReturnCorrectLenderId() {
        Funding funding = new Funding("F001", "L001", "LN001");
        assertEquals("LN001", funding.getLenderId());
    }
}