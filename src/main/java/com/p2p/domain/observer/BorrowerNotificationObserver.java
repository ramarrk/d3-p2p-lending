// Notifies borrower when their loan is fully funded
package com.p2p.domain.observer;

import com.p2p.domain.model.Loan;

public class BorrowerNotificationObserver implements FundingObserver {

    @Override
    public void onFundingComplete(Loan loan) {
        System.out.println("Notifikasi ke borrower "
                + loan.getBorrower().getName()
                + ": Dana pinjaman kamu sudah terkumpul penuh, siap dicairkan!");
    }
}