# BDD scenarios for Loan Funding domain
Feature: Loan Funding

  Scenario: Lender successfully funds part of a loan
    Given a loan exists with a target amount of 10000000
    When the lender funds the loan with 5000000
    Then the total funded amount becomes 5000000
    And the loan status remains FUNDING

  Scenario: Full funding triggers observer notification
    Given a loan exists with a target amount of 10000000
    And the total funded amount is already 9000000
    When the lender funds the loan with 1000000
    Then the total funded amount becomes 10000000
    And all observers are notified of funding completion

  Scenario: Funding that exceeds target is rejected
    Given a loan exists with a target amount of 10000000
    And the total funded amount is already 9000000
    When the lender funds the loan with 2000000
    Then the system rejects with error "Total funding melebihi target pinjaman"

  Scenario: Loan is cancelled when funding is 50 percent complete
    Given a loan exists with a target amount of 10000000
    And the total funded amount is already 5000000
    And 2 lenders have already contributed
    When the borrower cancels the loan
    Then the loan status becomes CANCELLED
    And all lenders receive a proportional refund