package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Lender;
import com.p2p.domain.repository.LenderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryLenderRepository implements LenderRepository {
    private final Map<String, Lender> database = new HashMap<>();

    @Override
    public void save(Lender lender) {
        database.put(lender.getId(), lender);
    }

    @Override
    public Lender findById(String id) {
        return database.get(id);
    }

    @Override
    public List<Lender> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void delete(String id) {
        database.remove(id);
    }
}
