package com.p2p.domain.model;

import com.p2p.domain.valueobject.Money;
import com.p2p.domain.valueobject.RepaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Repayment {
    private final String id;
    private final String loanId;
    private final LocalDate dueDate;
    private final Money amount;
    private RepaymentStatus status;
    private Money penalty;
    private LocalDateTime paidAt;

    public Repayment(String id, String loanId, LocalDate dueDate, Money amount) {
        this.id = id;
        this.loanId = loanId;
        this.dueDate = dueDate;
        this.amount = amount;
        this.status = RepaymentStatus.PENDING;
        this.penalty = new Money(BigDecimal.ZERO);
    }

    public void markAsPaid() {
        this.status = RepaymentStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }

    public void calculatePenalty(LocalDate paymentDate) {
        if (paymentDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, paymentDate);
            this.penalty = new Money(BigDecimal.valueOf(daysLate * 50000));
            this.status = RepaymentStatus.LATE;
        } else {
            this.penalty = new Money(BigDecimal.ZERO);
        }
    }

    public String getId() { return id; }
    public String getLoanId() { return loanId; }
    public LocalDate getDueDate() { return dueDate; }
    public Money getAmount() { return amount; }
    public RepaymentStatus getStatus() { return status; }
    public Money getPenalty() { return penalty; }
    public LocalDateTime getPaidAt() { return paidAt; }
}