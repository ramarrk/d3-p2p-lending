package com.p2p;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.model.Loan;
import com.p2p.domain.model.Repayment;
import com.p2p.domain.observer.BorrowerNotificationObserver;
import com.p2p.domain.observer.DisbursementObserver;
import com.p2p.domain.observer.LenderNotificationObserver;
import com.p2p.domain.risk.ActiveLoanHandler;
import com.p2p.domain.risk.BorrowingLimitHandler;
import com.p2p.domain.risk.CreditScoreHandler;
import com.p2p.domain.risk.RiskHandler;
import com.p2p.domain.strategy.FixedRateStrategy;
import com.p2p.domain.strategy.FloatingRateStrategy;
import com.p2p.domain.strategy.MurabahahStrategy;
import com.p2p.domain.valueobject.InterestRate;
import com.p2p.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("=== P2P Lending Platform Simulation ===");

        Borrower borrower = new Borrower(
                "B001",
                "Andi Pratama",
                "andi@mail.com",
                750,
                new Money(new BigDecimal("50000000"))
        );
        logger.info("Borrower created: " + borrower.getName());
        logger.info("Credit Score: " + borrower.getCreditScore());
        logger.info("Borrowing Limit: " + borrower.getBorrowingLimit());

        Loan loan = new Loan("L001");
        logger.info("Loan created with ID: " + loan.getId());
        logger.info("Loan status: " + loan.getState().getClass().getSimpleName());

        loan.addObserver(new LenderNotificationObserver());
        loan.addObserver(new BorrowerNotificationObserver());
        loan.addObserver(new DisbursementObserver());
        logger.info("Observers registered.");

        logger.info("=== Risk Engine Check ===");
        RiskHandler chain = new CreditScoreHandler();
        chain.setNext(new BorrowingLimitHandler());
        chain.setNext(new ActiveLoanHandler());

        try {
            chain.handle(borrower, new Money(new BigDecimal("10000000")));
            logger.info("Risk check passed!");
            loan.approve();
            logger.info("Loan approved. Status: " + loan.getState().getClass().getSimpleName());
            loan.startFunding();
            logger.info("Funding started. Status: " + loan.getState().getClass().getSimpleName());
        } catch (Exception e) {
            logger.info("Risk check failed: " + e.getMessage());
            loan.reject();
            logger.info("Loan rejected. Status: " + loan.getState().getClass().getSimpleName());
            return;
        }

        logger.info("=== Disbursement ===");
        loan.disburse();
        logger.info("Loan disbursed. Status: " + loan.getState().getClass().getSimpleName());

        logger.info("=== Repayment ===");
        loan.startRepayment();
        logger.info("Repayment started. Status: " + loan.getState().getClass().getSimpleName());

        logger.info("=== Repayment Schedule (Fixed Rate 12%) ===");
        Money principal = new Money(new BigDecimal("10000000"));
        int tenor = 12;

        FixedRateStrategy fixedStrategy = new FixedRateStrategy(
                new InterestRate(new BigDecimal("0.12"))
        );
        List<Repayment> fixedSchedule = fixedStrategy.calculate(loan.getId(), principal, tenor);
        fixedSchedule.forEach(r ->
                logger.info("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        logger.info("=== Repayment Schedule (Floating Rate 10%) ===");
        FloatingRateStrategy floatingStrategy = new FloatingRateStrategy(new BigDecimal("0.10"));
        List<Repayment> floatingSchedule = floatingStrategy.calculate(loan.getId(), principal, tenor);
        floatingSchedule.forEach(r ->
                logger.info("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        logger.info("=== Repayment Schedule (Murabahah) ===");
        MurabahahStrategy murabahahStrategy = new MurabahahStrategy(new Money(new BigDecimal("2000000")));
        List<Repayment> murabahahSchedule = murabahahStrategy.calculate(loan.getId(), principal, tenor);
        murabahahSchedule.forEach(r ->
                logger.info("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        logger.info("=== Late Payment Simulation ===");
        Repayment firstRepayment = fixedSchedule.get(0);
        LocalDate latePaymentDate = firstRepayment.getDueDate().plusDays(5);
        firstRepayment.calculatePenalty(latePaymentDate);
        logger.info("Due date: " + firstRepayment.getDueDate());
        logger.info("Payment date: " + latePaymentDate);
        logger.info("Penalty (5 days late): " + firstRepayment.getPenalty());
        logger.info("Status: " + firstRepayment.getStatus());

        logger.info("=== Close Loan ===");
        loan.close();
        logger.info("Loan closed. Status: " + loan.getState().getClass().getSimpleName());

        logger.info("=== Simulation Complete ===");
    }
}