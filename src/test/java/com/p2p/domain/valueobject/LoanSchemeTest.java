package com.p2p.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanSchemeTest {
    @Test
    public void testLoanSchemeValues() {
        LoanScheme[] schemes = LoanScheme.values();
        assertTrue(schemes.length > 0);
        String firstName = schemes[0].name();
        assertNotNull(LoanScheme.valueOf(firstName));
    }
}
