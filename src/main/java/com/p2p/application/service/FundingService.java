package com.p2p.application.service;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.repository.FundingRepository;
import com.p2p.domain.repository.LoanRepository;
import com.p2p.domain.valueobject.Money;

import java.util.UUID;

public class FundingService {
    private final FundingRepository fundingRepository;
    private final LoanRepository loanRepository;

    public FundingService(FundingRepository fundingRepository,
                          LoanRepository loanRepository) {
        this.fundingRepository = fundingRepository;
        this.loanRepository = loanRepository;
    }

    public void fund(String loanId, String lenderId, Money amount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Loan tidak ditemukan: " + loanId));

        Funding funding = new Funding(
                UUID.randomUUID().toString(),
                loanId,
                lenderId,
                amount
        );

        loan.addFunding(funding);
        fundingRepository.save(funding);
    }
}