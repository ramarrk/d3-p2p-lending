package com.p2p.domain.repository;

import com.p2p.domain.model.Borrower;
import java.util.List;
import java.util.Optional;

public interface BorrowerRepository {
    void save(Borrower borrower);
    Optional<Borrower> findById(String id);
    List<Borrower> findAll();
    void delete(String id);
}