package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosedStateTest {

    @Test
    public void testClosedStateInstantiation() {
        ClosedState state = new ClosedState();
        assertNotNull(state);
    }
}