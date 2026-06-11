package com.p2p.domain.observer;

import com.p2p.domain.model.Loan;
import java.util.logging.Logger;

public class BorrowerNotificationObserver implements FundingObserver {
    private static final Logger logger = Logger.getLogger(BorrowerNotificationObserver.class.getName());

    @Override
    public void onFundingComplete(Loan loan) {
        logger.info("Notifikasi ke borrower "
                + loan.getBorrower().getName()
                + ": Dana pinjaman " + loan.getId()
                + " kamu sudah terkumpul penuh, siap dicairkan!");
    }
}