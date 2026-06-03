package com.p2p.domain.model;

import org.junit.jupiter.api.Test;
import com.p2p.domain.valueobject.Money;
import com.p2p.domain.valueobject.RepaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class RepaymentTest {

    @Test
    public void testRepaymentCreationAndGetters() {
        String id = "R1";
        String loanId = "L1";
        LocalDate dueDate = LocalDate.now().plusDays(7);
        Money amount = new Money(new BigDecimal("1000000"));

        Repayment repayment = new Repayment(id, loanId, dueDate, amount);

        assertEquals(id, repayment.getId());
        assertEquals(loanId, repayment.getLoanId());
        assertEquals(dueDate, repayment.getDueDate());
        assertEquals(amount, repayment.getAmount());
        assertEquals(RepaymentStatus.PENDING, repayment.getStatus());
        assertNotNull(repayment.getPenalty());
        assertNull(repayment.getPaidAt());
    }

    @Test
    public void testMarkAsPaid() {
        Repayment repayment = new Repayment("R1", "L1", LocalDate.now(), new Money(BigDecimal.TEN));

        repayment.markAsPaid();

        assertEquals(RepaymentStatus.PAID, repayment.getStatus());
        assertNotNull(repayment.getPaidAt());
    }

    @Test
    public void testCalculatePenaltyWhenLate() {
        LocalDate dueDate = LocalDate.now().minusDays(3);
        Repayment repayment = new Repayment("R1", "L1", dueDate, new Money(BigDecimal.TEN));

        LocalDate paymentDate = LocalDate.now();
        repayment.calculatePenalty(paymentDate);

        assertNotNull(repayment.getPenalty());
        assertEquals(RepaymentStatus.LATE, repayment.getStatus());
    }

    @Test
    public void testCalculatePenaltyWhenOnTime() {
        LocalDate dueDate = LocalDate.now().plusDays(2);
        Repayment repayment = new Repayment("R1", "L1", dueDate, new Money(BigDecimal.TEN));

        LocalDate paymentDate = LocalDate.now();
        repayment.calculatePenalty(paymentDate);

        assertNotNull(repayment.getPenalty());
        assertEquals(RepaymentStatus.PENDING, repayment.getStatus());
    }
}