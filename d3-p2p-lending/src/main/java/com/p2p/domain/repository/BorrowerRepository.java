package com.p2p.domain.repository;

import com.p2p.domain.model.Borrower;

public interface BorrowerRepository {
    void save(Borrower borrower);
    Borrower findById(String id);
}