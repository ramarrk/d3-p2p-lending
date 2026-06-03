package com.p2p.domain.repository;

import com.p2p.domain.model.Funding;
import java.util.List;
import java.util.Optional;

public interface FundingRepository {
    void save(Funding funding);
    Optional<Funding> findById(String id);
    List<Funding> findByLoanId(String loanId);
    void delete(String id);
}