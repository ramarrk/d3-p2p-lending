package com.p2p.domain;

import com.p2p.domain.model.Repayment;
import com.p2p.domain.strategy.FixedRateStrategy;
import com.p2p.domain.strategy.MurabahahStrategy;
import com.p2p.domain.strategy.FloatingRateStrategy;
import com.p2p.domain.strategy.RepaymentStrategyFactory;
import com.p2p.domain.valueobject.InterestRate;
import com.p2p.domain.valueobject.LoanScheme;
import com.p2p.domain.valueobject.Money;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

public class RepaymentStrategyTest {

    // =====================
    // TEST FIXED RATE
    // =====================
    @Test
    void testFixedRate_jumlahCicilan() {
        // Pokok 10 juta, rate 12%/tahun, tenor 12 bulan
        Money principal = new Money(new BigDecimal("10000000"));
        InterestRate rate = new InterestRate(new BigDecimal("0.12"));
        int tenor = 12;

        FixedRateStrategy strategy = new FixedRateStrategy(rate);
        List<Repayment> jadwal = strategy.calculate("loan-001", principal, tenor);

        // Harus ada 12 cicilan
        assertEquals(12, jadwal.size());
    }

    @Test
    void testFixedRate_jumlahCicilanPerBulan() {
        // Rumus: (10.000.000 + 10.000.000 × 0.12 × 12/12) / 12
        // = (10.000.000 + 1.200.000) / 12 = 933.333,33
        Money principal = new Money(new BigDecimal("10000000"));
        InterestRate rate = new InterestRate(new BigDecimal("0.12"));

        FixedRateStrategy strategy = new FixedRateStrategy(rate);
        List<Repayment> jadwal = strategy.calculate("loan-001", principal, 12);

        BigDecimal cicilan = jadwal.get(0).getAmount().getAmount();
        assertEquals(new BigDecimal("933333.33"), cicilan);
    }

    // =====================
    // TEST MURABAHAH
    // =====================
    @Test
    void testMurabahah_jumlahCicilan() {
        // Pokok 10 juta, margin 2 juta, tenor 12 bulan
        Money principal = new Money(new BigDecimal("10000000"));
        Money margin = new Money(new BigDecimal("2000000"));

        MurabahahStrategy strategy = new MurabahahStrategy(margin);
        List<Repayment> jadwal = strategy.calculate("loan-002", principal, 12);

        // Harus ada 12 cicilan
        assertEquals(12, jadwal.size());
    }

    @Test
    void testMurabahah_jumlahCicilanPerBulan() {
        // Rumus: (10.000.000 + 2.000.000) / 12 = 1.000.000
        Money principal = new Money(new BigDecimal("10000000"));
        Money margin = new Money(new BigDecimal("2000000"));

        MurabahahStrategy strategy = new MurabahahStrategy(margin);
        List<Repayment> jadwal = strategy.calculate("loan-002", principal, 12);

        BigDecimal cicilan = jadwal.get(0).getAmount().getAmount();
        assertEquals(new BigDecimal("1000000.00"), cicilan);
    }

    // =====================
    // TEST FLOATING RATE
    // =====================
    @Test
    void testFloatingRate_jumlahCicilan() {
        Money principal = new Money(new BigDecimal("10000000"));
        BigDecimal marketRate = new BigDecimal("0.12");

        FloatingRateStrategy strategy = new FloatingRateStrategy(marketRate);
        List<Repayment> jadwal = strategy.calculate("loan-003", principal, 12);

        // Harus ada 12 cicilan
        assertEquals(12, jadwal.size());
    }

    @Test
    void testFloatingRate_cicilanMengecilTiapBulan() {
        // Floating rate: cicilan bulan pertama lebih besar dari bulan terakhir
        // karena bunga dihitung dari sisa pokok
        Money principal = new Money(new BigDecimal("10000000"));
        BigDecimal marketRate = new BigDecimal("0.12");

        FloatingRateStrategy strategy = new FloatingRateStrategy(marketRate);
        List<Repayment> jadwal = strategy.calculate("loan-003", principal, 12);

        BigDecimal cicilanPertama = jadwal.get(0).getAmount().getAmount();
        BigDecimal cicilanTerakhir = jadwal.get(11).getAmount().getAmount();

        assertTrue(cicilanPertama.compareTo(cicilanTerakhir) > 0);
    }

    // =====================
    // TEST FACTORY
    // =====================
    @Test
    void testFactory_pilihanFixed() {
        InterestRate rate = new InterestRate(new BigDecimal("0.12"));
        var strategy = RepaymentStrategyFactory.create(
            LoanScheme.FIXED, rate, null, null
        );
        assertInstanceOf(FixedRateStrategy.class, strategy);
    }

    @Test
    void testFactory_pilihanMurabahah() {
        Money margin = new Money(new BigDecimal("2000000"));
        var strategy = RepaymentStrategyFactory.create(
            LoanScheme.MURABAHAH, null, margin, null
        );
        assertInstanceOf(MurabahahStrategy.class, strategy);
    }

    @Test
    void testFactory_pilihanFloating() {
        BigDecimal marketRate = new BigDecimal("0.12");
        var strategy = RepaymentStrategyFactory.create(
            LoanScheme.FLOATING, null, null, marketRate
        );
        assertInstanceOf(FloatingRateStrategy.class, strategy);
    }
}