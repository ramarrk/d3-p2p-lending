package com.p2p.StepDefinition;

import com.p2p.domain.model.Funding;
import com.p2p.domain.model.Loan;
import com.p2p.domain.valueobject.Money;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class FundingSteps {
    private Loan loan;
    private Exception thrownException;
    private boolean observerNotified = false;
}