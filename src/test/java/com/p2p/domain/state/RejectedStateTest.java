package com.p2p.domain.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RejectedStateTest {

    @Test
    public void testRejectedStateInstantiation() {
        RejectedState state = new RejectedState();
        assertNotNull(state);
    }
}