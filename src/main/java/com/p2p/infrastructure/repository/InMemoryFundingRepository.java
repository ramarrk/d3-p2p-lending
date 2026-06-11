package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Funding;
import com.p2p.domain.repository.FundingRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryFundingRepository implements FundingRepository {
    private final Map<String, Funding> store = new HashMap<>();

    @Override
    public void save(Funding funding) {}

    @Override
    public Optional<Funding> findById(String id) { return Optional.empty(); }

    @Override
    public List<Funding> findByLoanId(String loanId) { return new ArrayList<>(); }

    @Override
    public void delete(String id) {}
}