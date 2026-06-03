package com.p2p.StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.p2p.domain.model.Loan;
import com.p2p.domain.state.FundingState;

public class FundingSteps {
    private Loan loan;
    private Exception caughtException; 

    @Given("a loan exists with a target amount of {int}")
    public void a_loan_exists_with_a_target_amount_of(Integer target) {
        loan = new Loan(target);
        loan.setState(new FundingState());
    }

    @Given("the total funded amount is already {int}")
    public void the_total_funded_amount_is_already(Integer funded) {
        loan.setTotalFundedAmount(funded);
    }

    @Then("all observers are notified of funding completion")
    public void all_observers_are_notified_of_funding_completion() {
        // Biarkan kosong dulu untuk fase RED
    }

    @Given("{int} lenders have already contributed")
    public void lenders_have_already_contributed(Integer int1) {
        // Biarkan kosong dulu
    }

    @When("the borrower cancels the loan")
    public void the_borrower_cancels_the_loan() {
        loan.cancel(); 
    }

    @Then("the loan status becomes CANCELLED")
    public void the_loan_status_becomes_cancelled() {
        String statusAktif = loan.getState().getClass().getSimpleName();
        assertEquals("CancelledState", statusAktif);
    }

    @Then("all lenders receive a proportional refund")
    public void all_lenders_receive_a_proportional_refund() {
        // Biarkan kosong dulu
    }

    @When("the lender funds the loan with {int}")
    public void the_lender_funds_the_loan_with(Integer amount) {
        try {
            loan.addFunding(amount); 
        } catch (Exception e) {
            this.caughtException = e; 
        }
    }

    @Then("the total funded amount becomes {int}")
    public void the_total_funded_amount_becomes(Integer expectedFunded) {
        assertEquals(expectedFunded, loan.getTotalFundedAmount());
    }

    @Then("the loan status remains FUNDING")
    public void the_loan_status_remains_funding() {
        String statusAktif = loan.getState().getClass().getSimpleName();
        assertEquals("FundingState", statusAktif); 
    }

    @Then("the system rejects with error {string}")
    public void the_system_rejects_with_error(String expectedMessage) {
        // 1. Pastikan erornya beneran ketangkap
        org.junit.jupiter.api.Assertions.assertNotNull(caughtException, "Harusnya sistem menolak, tapi malah lolos!");
        
        // 2. Samakan pesan erornya dengan tulisan dari skenario .feature
        String actualMessage = caughtException.getMessage();
        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains("melebihi target"));
    }
}