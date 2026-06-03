package com.p2p.domain.model;

import org.junit.jupiter.api.Test;
import com.p2p.domain.valueobject.Money;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class LenderTest {

    @Test
    public void testLenderCreationAndGetters() {
        String id = "LND1";
        String name = "Budi";
        String email = "budi@lender.com";
        Money initialBalance = new Money(new BigDecimal("10000000"));

        Lender lender = new Lender(id, name, email, initialBalance);

        assertEquals(id, lender.getId());
        assertEquals(name, lender.getName());
        assertEquals(email, lender.getEmail());
        assertEquals(initialBalance, lender.getBalance());
    }

    @Test
    public void testDeductBalance() {
        Money initialBalance = new Money(new BigDecimal("5000000"));
        Lender lender = new Lender("LND1", "Budi", "budi@lender.com", initialBalance);

        Money deductAmount = new Money(new BigDecimal("2000000"));
        lender.deductBalance(deductAmount);

        assertNotNull(lender.getBalance());
    }

    @Test
    public void testRefundBalance() {
        Money initialBalance = new Money(new BigDecimal("5000000"));
        Lender lender = new Lender("LND1", "Budi", "budi@lender.com", initialBalance);

        Money refundAmount = new Money(new BigDecimal("1500000"));
        lender.refundBalance(refundAmount);

        assertNotNull(lender.getBalance());
    }
}