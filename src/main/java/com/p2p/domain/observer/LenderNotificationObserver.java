// Notifies all lenders when a loan is fully funded
package com.p2p.domain.observer;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;

public class LenderNotificationObserver implements FundingObserver {

    @Override
    public void onFundingComplete(Loan loan) {
        for (Funding funding : loan.getFundings()) {
            System.out.println("Notifikasi ke lender " + funding.getLenderId()
                    + ": Loan " + loan.getId() + " telah penuh didanai!");
        }
    }
}