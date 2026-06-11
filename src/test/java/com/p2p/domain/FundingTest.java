package com.p2p.domain;

import com.p2p.domain.model.Funding;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import com.p2p.domain.model.Loan;

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

    @Test
    void shouldAddFundingToLoan() {
        Loan loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        loan.setTargetAmount(new Money(new BigDecimal("10000000")));

        Funding funding = new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("5000000")));
        loan.addFunding(funding);

        assertEquals(
                new BigDecimal("5000000").stripTrailingZeros(),
                loan.getTotalFunded().getAmount().stripTrailingZeros()
        );
    }

    @Test
    void shouldThrowExceptionWhenFundingExceedsTarget() {
        Loan loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        loan.setTargetAmount(new Money(new BigDecimal("10000000")));
        loan.addFunding(new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("9000000"))));

        assertThrows(com.p2p.domain.exception.ExcessFundingException.class, () ->
                loan.addFunding(new Funding("F002", "L001", "LN002",
                        new Money(new BigDecimal("2000000"))))
        );
    }

    @Test
    void shouldNotifyObserversWhenFullyFunded() {
        Loan loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        loan.setTargetAmount(new Money(new BigDecimal("10000000")));

        boolean[] notified = {false};
        loan.addObserver(l -> notified[0] = true);

        loan.addFunding(new Funding("F001", "L001", "LN001",
                new Money(new BigDecimal("10000000"))));

        assertTrue(notified[0]);
    }
}