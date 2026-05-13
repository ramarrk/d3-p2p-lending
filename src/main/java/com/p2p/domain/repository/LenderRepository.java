package com.p2p.domain.repository;

import com.p2p.domain.model.Lender;

public interface LenderRepository {
    void save(Lender lender);
    Lender findById(String id);
}