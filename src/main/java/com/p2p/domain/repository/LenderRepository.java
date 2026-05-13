package com.p2p.domain.repository;

import com.p2p.domain.model.Lender;
import java.util.List;

public interface LenderRepository {
    void save(Lender lender);
    Lender findById(String id);
    List<Lender> findAll();
    void delete(String id);
}