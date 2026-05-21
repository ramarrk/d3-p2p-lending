package com.p2p.StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class LoanSteps {

    // --- Skenario 1 ---

    @Given("Pinjaman baru dibuat dengan status {string}")
    public void pinjamanBaruDibuatDenganStatus(String status) {
        System.out.println("Membuat pinjaman baru dengan status: " + status);
    }

    @When("Admin menyetujui pinjaman")
    public void adminMenyetujuiPinjaman() {
        System.out.println("Admin menyetujui pinjaman.");
    }

    @And("Sistem membuka masa pendanaan")
    public void sistemMembukaMasaPendanaan() {
        System.out.println("Sistem membuka masa pendanaan.");
    }

    @Then("Status pinjaman saat ini harus {string}")
    public void statusPinjamanSaatIniHarus(String statusDiharapkan) {
        System.out.println("Verifikasi status: " + statusDiharapkan);
    }

    // --- Skenario 2 ---

    @Given("Pinjaman dalam status {string}")
    public void pinjamanDalamStatus(String status) {
        System.out.println("Pinjaman saat ini: " + status);
    }

    @When("Sistem mencoba mencairkan dana")
    public void sistemMencobaMencairkanDana() {
        System.out.println("Mencoba mencairkan dana...");
    }

    @Then("Sistem harus menolak transisi dengan error {string}")
    public void sistemHarusMenolakTransisiDenganError(String namaError) {
        System.out.println("Memverifikasi sistem menolak dengan error: " + namaError);
    }
}



