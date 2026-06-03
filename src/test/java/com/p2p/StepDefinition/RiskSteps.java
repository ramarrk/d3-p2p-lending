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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RiskSteps {

    private Borrower borrower;
    private Money loanAmount;
    private Exception exception;

    @Given("a borrower with credit score {int}")
    public void aBorrowerWithCreditScore(int creditScore) {
        borrower = new Borrower(
                "B001",
                "Test User",
                "test@mail.com",
                creditScore,
                new Money(BigDecimal.valueOf(50000000))
        );
    }

    @Given("a borrower with borrowing limit {int}")
    public void aBorrowerWithBorrowingLimit(int borrowingLimit) {
        borrower = new Borrower(
                "B001",
                "Test User",
                "test@mail.com",
                750,
                new Money(BigDecimal.valueOf(borrowingLimit))
        );
    }

    @Given("a borrower with {int} active loans")
    public void aBorrowerWithActiveLoans(int activeLoans) {
        borrower = new Borrower(
                "B001",
                "Test User",
                "test@mail.com",
                750,
                new Money(BigDecimal.valueOf(50000000))
        );

        for (int i = 0; i < activeLoans; i++) {
            borrower.incrementActiveLoan();
        }
    }

    @When("they apply for a loan of {int}")
    public void theyApplyForALoanOf(int amount) {
        loanAmount = new Money(BigDecimal.valueOf(amount));
        runRiskValidation();
    }

    @When("they apply for a new loan")
    public void theyApplyForANewLoan() {
        loanAmount = new Money(BigDecimal.valueOf(5000000));
        runRiskValidation();
    }

    private void runRiskValidation() {
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

    @Then("the application should be rejected with {string}")
    public void theApplicationShouldBeRejectedWith(String expectedException) {
        assertNotNull(exception);
        String actualException = exception.getClass().getSimpleName();
        assertEquals(expectedException, actualException);
    }
}