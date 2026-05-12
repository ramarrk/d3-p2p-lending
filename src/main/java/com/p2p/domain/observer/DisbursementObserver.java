// Triggers disbursement process when a loan is fully funded
package com.p2p.domain.observer;

import com.p2p.domain.model.Loan;

public class DisbursementObserver implements FundingObserver {

    @Override
    public void onFundingComplete(Loan loan) {
        System.out.println("Disbursement triggered untuk loan "
                + loan.getId()
                + ": Proses pencairan dana dimulai.");
    }
}