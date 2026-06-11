package com.p2p.StepDefinition;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
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

    @Then("the total funded amount becomes {long}")
    public void theTotalFundedAmountBecomes(long expected) {
        assertNull(thrownException, "Tidak seharusnya ada exception: " + thrownException);
        assertEquals(
                BigDecimal.valueOf(expected).stripTrailingZeros(),
                loan.getTotalFunded().getAmount().stripTrailingZeros()
        );
    }

    @Then("the loan status remains FUNDING")
    public void theLoanStatusRemainsFunding() {
        assertInstanceOf(com.p2p.domain.state.FundingState.class, loan.getState());
    }

    @Then("all observers are notified of funding completion")
    public void allObserversAreNotifiedOfFundingCompletion() {
        assertTrue(observerNotified, "Observer harus dipanggil saat funding penuh");
    }
}