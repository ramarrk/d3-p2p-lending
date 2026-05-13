package com.p2p.domain.repository;
import java.util.Optional;
import com.p2p.domain.model.Lender;
import java.util.List;

public interface LenderRepository {
    void save(Lender lender);
    Optional<Lender> findById(String id);
    List<Lender> findAll();
    void delete(String id);
}