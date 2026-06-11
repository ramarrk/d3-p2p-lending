package com.p2p.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryFundingRepositoryTest {
    private InMemoryFundingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryFundingRepository();
    }
}