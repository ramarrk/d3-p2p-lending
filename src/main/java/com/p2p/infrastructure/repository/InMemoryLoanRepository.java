package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Loan;
import com.p2p.domain.repository.LoanRepository;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> store = new HashMap<>();

    @Override public void save(Loan loan) {}
    @Override public Optional<Loan> findById(String id) { return Optional.empty(); }
    @Override public List<Loan> findAll() { return new ArrayList<>(); }
    @Override public List<Loan> findByBorrowerId(String borrowerId) { return new ArrayList<>(); }
    @Override public void delete(String id) {}
}