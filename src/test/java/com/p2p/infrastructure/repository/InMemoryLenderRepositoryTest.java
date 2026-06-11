package com.p2p.infrastructure.repository;

import com.p2p.domain.model.Lender;
import com.p2p.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryLenderRepositoryTest {
    private InMemoryLenderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLenderRepository();
    }
}