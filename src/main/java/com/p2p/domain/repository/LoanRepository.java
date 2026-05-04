package com.p2p.domain.repository;

import com.p2p.domain.model.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findById(String id);
    List<Loan> findAll();
    List<Loan> findByBorrowerId(String borrowerId);
    void delete(String id);
}