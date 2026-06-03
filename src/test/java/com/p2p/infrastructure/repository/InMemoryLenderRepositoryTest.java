package com.p2p.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.model.Lender;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryLenderRepositoryTest {

    @Test
    public void testSaveAndFindById() {
        InMemoryLenderRepository repository = new InMemoryLenderRepository();
        Lender mockLender = Mockito.mock(Lender.class);
        Mockito.when(mockLender.getId()).thenReturn("LENDER-777");

        repository.save(mockLender);
        Optional<Lender> result = repository.findById("LENDER-777");

        assertTrue(result.isPresent());
        assertEquals(mockLender, result.get());
    }

    @Test
    public void testFindAll() {
        InMemoryLenderRepository repository = new InMemoryLenderRepository();
        Lender mockLender = Mockito.mock(Lender.class);
        Mockito.when(mockLender.getId()).thenReturn("LEN");

        repository.save(mockLender);
        List<Lender> result = repository.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testDelete() {
        InMemoryLenderRepository repository = new InMemoryLenderRepository();
        Lender mockLender = Mockito.mock(Lender.class);
        Mockito.when(mockLender.getId()).thenReturn("LEN-DEL");

        repository.save(mockLender);
        repository.delete("LEN-DEL");
        Optional<Lender> result = repository.findById("LEN-DEL");

        assertFalse(result.isPresent());
    }
}