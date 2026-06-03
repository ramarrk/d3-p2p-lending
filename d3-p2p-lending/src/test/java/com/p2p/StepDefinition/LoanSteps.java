package com.p2p.StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.p2p.domain.model.Loan;
import com.p2p.domain.state.PendingState;

public class LoanSteps {
    private Loan loan;
    private Exception exceptionThrown;

    // --- Skenario 2: Pencegahan pencairan dana prematur ---

    @Given("Pinjaman dalam status {string}")
    public void pinjamanDalamStatusPending(String status) {
        loan = new Loan(status);
        loan.setState(new PendingState()); // Set state awal ke Pending
    }

    @When("Sistem mencoba mencairkan dana")
    public void sistemMencobaMencairkanDana() {
        // Kita paksa panggil fungsi disburse() saat status masih PENDING
        // Kita tangkap exception-nya menggunakan assertThrows atau try-catch
        exceptionThrown = assertThrows(IllegalStateException.class, () -> {
            loan.disburse(); 
        });
    }

    @Then("Sistem harus menolak transisi dengan error {string}")
    public void sistemHarusMenolakTransisiDenganError(String pesanErrorDiharapkan) {
        // Validasi apakah pesan error yang keluar sesuai dengan di file .feature
        assertEquals(pesanErrorDiharapkan, exceptionThrown.getMessage());
    }
}