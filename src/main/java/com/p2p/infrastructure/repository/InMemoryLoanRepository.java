package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Loan;
import com.p2p.domain.repository.LoanRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> store = new HashMap<>();

    @Override
    public void save(Loan loan) {
        store.put(loan.getId(), loan);
    }

    @Override
    public Optional<Loan> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Loan> findByBorrowerId(String borrowerId) {
        return store.values().stream()
                .filter(loan -> loan.getBorrower() != null
                        && borrowerId.equals(loan.getBorrower().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}
