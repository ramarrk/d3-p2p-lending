package com.p2p.domain.observer;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import java.util.logging.Logger;

public class LenderNotificationObserver implements FundingObserver {
    private static final Logger logger = Logger.getLogger(LenderNotificationObserver.class.getName());

    @Override
    public void onFundingComplete(Loan loan) {
        for (Funding funding : loan.getFundings()) {
            logger.info("Notifikasi ke lender " + funding.getLenderId()
                    + ": Loan " + loan.getId() + " telah penuh didanai!");
        }
    }
}