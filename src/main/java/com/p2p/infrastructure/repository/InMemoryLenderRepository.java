package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Lender;
import com.p2p.domain.repository.LenderRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryLenderRepository implements LenderRepository {
    private final Map<String, Lender> database = new HashMap<>();

    @Override
    public void save(Lender lender) {
        database.put(lender.getId(), lender);
    }

    @Override
    public Optional<Lender> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Lender> findAll() {
        return Optional.ofNullable(database.get())
                    .map(List::of)
                    .orElse(Collections.emptyList());
    }

    @Override
    public void delete(String id){
        database.clear();
    }
}
