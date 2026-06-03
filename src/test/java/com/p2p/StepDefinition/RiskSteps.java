package com.p2p.StepDefinition;

import com.p2p.domain.model.Borrower;
import com.p2p.domain.risk.ActiveLoanHandler;
import com.p2p.domain.risk.BorrowingLimitHandler;
import com.p2p.domain.risk.CreditScoreHandler;
import com.p2p.domain.risk.RiskHandler;
import com.p2p.domain.valueobject.Money;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RiskSteps {

    private Borrower borrower;
    private Money loanAmount;
    private Exception exception;

    @Given("a borrower with credit score {int}, borrowing limit {int}, and {int} active loans")
    public void aBorrowerWithCreditScoreBorrowingLimitAndActiveLoans(
            int creditScore,
            int borrowingLimit,
            int activeLoans) {

        borrower = new Borrower(
                "B001",
                "Test User",
                "test@mail.com",
                creditScore,
                new Money(BigDecimal.valueOf(borrowingLimit))
        );

        for (int i = 0; i < activeLoans; i++) {
            borrower.incrementActiveLoan();
        }
    }

    @When("the borrower applies for a loan of {int}")
    public void theBorrowerAppliesForALoanOf(int amount) {

        loanAmount = new Money(BigDecimal.valueOf(amount));

        RiskHandler creditHandler = new CreditScoreHandler();
        RiskHandler limitHandler = new BorrowingLimitHandler();
        RiskHandler activeLoanHandler = new ActiveLoanHandler();

        creditHandler.setNext(limitHandler);
        limitHandler.setNext(activeLoanHandler);

        try {
            creditHandler.handle(borrower, loanAmount);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the application should be approved")
    public void theApplicationShouldBeApproved() {
        assertNull(exception);
    }

    @Then("the application should be rejected")
    public void theApplicationShouldBeRejected() {
        assertNotNull(exception);
    }
}