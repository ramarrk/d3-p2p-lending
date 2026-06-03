package com.p2p.domain.observer;

import com.p2p.domain.model.Loan;

public interface FundingObserver {
    void onFundingComplete(Loan loan);
}