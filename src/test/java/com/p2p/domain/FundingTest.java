package com.p2p.domain;

import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FundingTest {
    @Test
    void shouldReturnCorrectId() {
        Funding funding = new Funding("F001");
        assertEquals("F001", funding.getId());
    }
}