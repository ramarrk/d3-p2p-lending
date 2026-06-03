package com.p2p.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BorrowingLimitExceededExceptionTest {

    @Test
    public void testCustomMessageConstructor() {
        String customMessage = "Pesan error kustom";
        BorrowingLimitExceededException exception = new BorrowingLimitExceededException(customMessage);
        assertEquals(customMessage, exception.getMessage());
    }
}