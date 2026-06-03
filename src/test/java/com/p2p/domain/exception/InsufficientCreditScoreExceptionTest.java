package com.p2p.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InsufficientCreditScoreExceptionTest {

    @Test
    public void testInsufficientCreditScoreExceptionMessage() {
        String message = "Custom credit score error";
        InsufficientCreditScoreException exception = new InsufficientCreditScoreException(message);
        assertEquals(message, exception.getMessage());
    }
}