package com.p2p;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.model.Funding;
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

public class Main {
    public static void main(String[] args) {
        System.out.println("=== P2P Lending Platform Simulation ===\n");

        // Setup Borrower
        Borrower borrower = new Borrower(
                "B001",
                "Andi Pratama",
                "andi@mail.com",
                750,
                new Money(new BigDecimal("50000000"))
        );
        System.out.println("Borrower created: " + borrower.getName());
        System.out.println("Credit Score: " + borrower.getCreditScore());
        System.out.println("Borrowing Limit: " + borrower.getBorrowingLimit());

        // Setup Loan
        Loan loan = new Loan("L001");
        System.out.println("\nLoan created with ID: " + loan.getId());
        System.out.println("Loan status: " + loan.getState().getClass().getSimpleName());

        // Register Observers
//        loan.addObserver(new LenderNotificationObserver());
        loan.addObserver(new BorrowerNotificationObserver());
        loan.addObserver(new DisbursementObserver());
        System.out.println("Observers registered.");

        // Risk Engine Check
        System.out.println("\n=== Risk Engine Check ===");
        RiskHandler chain = new CreditScoreHandler();
        chain.setNext(new BorrowingLimitHandler());
        chain.setNext(new ActiveLoanHandler());

        try {
            chain.handle(borrower, new Money(new BigDecimal("10000000")));
            System.out.println("Risk check passed!");

            // Approve Loan
            loan.approve();
            System.out.println("Loan approved. Status: " + loan.getState().getClass().getSimpleName());

            // Start Funding
            loan.startFunding();
            System.out.println("Funding started. Status: " + loan.getState().getClass().getSimpleName());

        } catch (Exception e) {
            System.out.println("Risk check failed: " + e.getMessage());
            loan.reject();
            System.out.println("Loan rejected. Status: " + loan.getState().getClass().getSimpleName());
            return;
        }

        // Simulate Funding
        System.out.println("\n=== Funding Process ===");

//        Money funding1Amount = new Money(new BigDecimal("6000000"));
//        Funding funding1 = new Funding("F001", loan.getId(), "LN001", funding1Amount);
//        loan.addFunding(funding1);
//        System.out.println("Lender 1 funded: " + funding1Amount);
//        System.out.println("Total funded: " + loan.getTotalFunded());
//        System.out.println("Fully funded: " + loan.isFullyFunded());
//
//        Money funding2Amount = new Money(new BigDecimal("4000000"));
//        Funding funding2 = new Funding("F002", loan.getId(), "LN002", funding2Amount);
//        loan.addFunding(funding2);
//        System.out.println("Lender 2 funded: " + funding2Amount);
//        System.out.println("Total funded: " + loan.getTotalFunded());
//        System.out.println("Fully funded: " + loan.isFullyFunded());

        // Disburse
        System.out.println("\n=== Disbursement ===");
        loan.disburse();
        System.out.println("Loan disbursed. Status: " + loan.getState().getClass().getSimpleName());

        // Start Repayment
        System.out.println("\n=== Repayment ===");
        loan.startRepayment();
        System.out.println("Repayment started. Status: " + loan.getState().getClass().getSimpleName());

        // Repayment Schedule - Fixed Rate
        System.out.println("\n=== Repayment Schedule (Fixed Rate 12%) ===");
        Money principal = new Money(new BigDecimal("10000000"));
        int tenor = 12;

        FixedRateStrategy fixedStrategy = new FixedRateStrategy(
                new InterestRate(new BigDecimal("0.12"))
        );
        List<Repayment> fixedSchedule = fixedStrategy.calculate(loan.getId(), principal, tenor);
        fixedSchedule.forEach(r ->
                System.out.println("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        // Repayment Schedule - Floating Rate
        System.out.println("\n=== Repayment Schedule (Floating Rate 10%) ===");
        FloatingRateStrategy floatingStrategy = new FloatingRateStrategy(
                new BigDecimal("0.10")
        );
        List<Repayment> floatingSchedule = floatingStrategy.calculate(loan.getId(), principal, tenor);
        floatingSchedule.forEach(r ->
                System.out.println("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        // Repayment Schedule - Murabahah
        System.out.println("\n=== Repayment Schedule (Murabahah) ===");
        MurabahahStrategy murabahahStrategy = new MurabahahStrategy(
                new Money(new BigDecimal("2000000"))
        );
        List<Repayment> murabahahSchedule = murabahahStrategy.calculate(loan.getId(), principal, tenor);
        murabahahSchedule.forEach(r ->
                System.out.println("Due: " + r.getDueDate() + " | Amount: " + r.getAmount())
        );

        // Simulate Late Payment Penalty
        System.out.println("\n=== Late Payment Simulation ===");
        Repayment firstRepayment = fixedSchedule.get(0);
        LocalDate latePaymentDate = firstRepayment.getDueDate().plusDays(5);
        firstRepayment.calculatePenalty(latePaymentDate);
        System.out.println("Due date: " + firstRepayment.getDueDate());
        System.out.println("Payment date: " + latePaymentDate);
        System.out.println("Penalty (5 days late): " + firstRepayment.getPenalty());
        System.out.println("Status: " + firstRepayment.getStatus());

        // Close Loan
        System.out.println("\n=== Close Loan ===");
        loan.close();
        System.out.println("Loan closed. Status: " + loan.getState().getClass().getSimpleName());

        System.out.println("\n=== Simulation Complete ===");
    }
}