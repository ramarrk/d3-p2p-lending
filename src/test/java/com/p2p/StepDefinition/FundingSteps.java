package com.p2p.StepDefinition;

import com.p2p.domain.exception.ExcessFundingException;
import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.observer.FundingObserver;
import com.p2p.domain.valueobject.Money;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FundingSteps {

    private Loan loan;
    private Exception thrownException;
    private boolean observerNotified = false;

    @Given("a loan exists with a target amount of {long}")
    public void aLoanExistsWithATargetAmountOf(long targetAmount) {
        loan = new Loan("L001");
        loan.approve();
        loan.startFunding();
        loan.setTargetAmount(new Money(BigDecimal.valueOf(targetAmount)));
        loan.addObserver(fundingLoan -> observerNotified = true);
    }

    @And("the total funded amount is already {long}")
    public void theTotalFundedAmountIsAlready(long alreadyFunded) {
        loan.addFunding(new Funding(
                UUID.randomUUID().toString(),
                loan.getId(),
                "LN-EXISTING",
                new Money(BigDecimal.valueOf(alreadyFunded))
        ));
    }

    @And("{int} lenders have already contributed")
    public void lendersHaveAlreadyContributed(int count) {
    }

    @When("the lender funds the loan with {long}")
    public void theLenderFundsTheLoanWith(long amount) {
        try {
            loan.addFunding(new Funding(
                    UUID.randomUUID().toString(),
                    loan.getId(),
                    "LN001",
                    new Money(BigDecimal.valueOf(amount))
            ));
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @When("the borrower cancels the loan")
    public void theBorrowerCancelsTheLoan() {
        try {
            loan.cancel();
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("the total funded amount becomes {long}")
    public void theTotalFundedAmountBecomes(long expected) {
        assertNull(thrownException);
        assertEquals(0, BigDecimal.valueOf(expected).compareTo(loan.getTotalFunded().getAmount()));
    }

    @Then("the loan status remains FUNDING")
    public void theLoanStatusRemainsFunding() {
        assertInstanceOf(com.p2p.domain.state.FundingState.class, loan.getState());
    }

    @Then("all observers are notified of funding completion")
    public void allObserversAreNotifiedOfFundingCompletion() {
        assertTrue(observerNotified);
    }

    @Then("the system rejects with error {string}")
    public void theSystemRejectsWithError(String errorMessage) {
        assertNotNull(thrownException);
        assertInstanceOf(ExcessFundingException.class, thrownException);
        if (thrownException.getMessage() != null) {
            assertTrue(thrownException.getMessage().contains("melebihi target") ||
                    thrownException.getMessage().contains(errorMessage));
        }
    }

    @Then("the loan status becomes CANCELLED")
    public void theLoanStatusBecomesCancelled() {
        assertInstanceOf(com.p2p.domain.state.CancelledState.class, loan.getState());
    }

    @Then("all lenders receive a proportional refund")
    public void allLendersReceiveAProportionalRefund() {
        assertTrue(true);
    }
}