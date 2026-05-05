package com.p2p.application;

import com.p2p.domain.exception.ExcessFundingException;
import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.repository.FundingRepository;
import com.p2p.domain.repository.LoanRepository;
import com.p2p.domain.valueobject.Money;
import com.p2p.application.service.FundingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundingServiceTest {

    @Mock
    FundingRepository fundingRepository;

    @Mock
    LoanRepository loanRepository;

    @InjectMocks
    FundingService fundingService;

    @Test
    void shouldSaveFunding_whenAmountValid() {
        // ARRANGE
        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn("L001");
        when(loan.isFullyFunded()).thenReturn(false);
        when(loanRepository.findById("L001")).thenReturn(Optional.of(loan));

        Money amount = new Money(new BigDecimal("3000000"));

        // ACT
        fundingService.fund("L001", "LN001", amount);

        // ASSERT
        verify(fundingRepository, times(1)).save(any(Funding.class));
        verify(loan, times(1)).addFunding(any(Funding.class));
    }

    @Test
    void shouldThrowException_whenLoanNotFound() {
        // ARRANGE
        when(loanRepository.findById("L999")).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () ->
                fundingService.fund("L999", "LN001",
                        new Money(new BigDecimal("3000000")))
        );
    }

    @Test
    void shouldThrowException_whenFundingExceedsTarget() {
        // ARRANGE
        Loan loan = mock(Loan.class);
        when(loanRepository.findById("L001")).thenReturn(Optional.of(loan));
        doThrow(new ExcessFundingException())
                .when(loan).addFunding(any(Funding.class));

        // ACT & ASSERT
        assertThrows(ExcessFundingException.class, () ->
                fundingService.fund("L001", "LN001",
                        new Money(new BigDecimal("99000000")))
        );
    }
}