package com.p2p.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActiveLoanLimitExceptionTest {

    @Test
    public void testActiveLoanLimitExceptionMessage() {
        String message = "Custom active loan error";
        ActiveLoanLimitException exception = new ActiveLoanLimitException(message);
        assertEquals(message, exception.getMessage());
    }
}