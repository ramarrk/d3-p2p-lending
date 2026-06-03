package com.p2p.domain.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.p2p.domain.valueobject.InterestRate;
import com.p2p.domain.valueobject.LoanScheme;
import com.p2p.domain.valueobject.Money;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class RepaymentStrategyFactoryTest {

    @Test
    public void testCreateFixedRateStrategy() {
        InterestRate mockRate = Mockito.mock(InterestRate.class);
        RepaymentCalculationStrategy strategy = RepaymentStrategyFactory.create(
                LoanScheme.FIXED, mockRate, null, null
        );
        assertTrue(strategy instanceof FixedRateStrategy);
    }

    @Test
    public void testCreateFloatingRateStrategy() {
        BigDecimal marketRate = new BigDecimal("0.11");
        RepaymentCalculationStrategy strategy = RepaymentStrategyFactory.create(
                LoanScheme.FLOATING, null, null, marketRate
        );
        assertTrue(strategy instanceof FloatingRateStrategy);
    }

    @Test
    public void testCreateMurabahahStrategy() {
        Money margin = new Money(new BigDecimal("1000000"));
        RepaymentCalculationStrategy strategy = RepaymentStrategyFactory.create(
                LoanScheme.MURABAHAH, null, margin, null
        );
        assertTrue(strategy instanceof MurabahahStrategy);
    }

    @Test
    public void testCreateNullSchemeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RepaymentStrategyFactory.create(null, null, null, null);
        });
    }

    @Test
    public void testCreateUnknownSchemeThrowsException() {
        LoanScheme mockUnknown = Mockito.mock(LoanScheme.class);
        Mockito.when(mockUnknown.name()).thenReturn("UNKNOWN");

        assertThrows(IllegalArgumentException.class, () -> {
            RepaymentStrategyFactory.create(mockUnknown, null, null, null);
        });
    }

    @Test
    public void testPrivateConstructorThrowsException() throws Exception {
        Constructor<RepaymentStrategyFactory> constructor =
                RepaymentStrategyFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });

        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("Utility class", exception.getCause().getMessage());
    }
}