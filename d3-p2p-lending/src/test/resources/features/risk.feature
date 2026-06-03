Feature: Risk Validation for Loan Application

  Scenario: Borrower with low credit score is rejected
    Given a borrower with credit score 500
    When they apply for a loan of 5000000
    Then the application should be rejected with "InsufficientCreditScoreException"

  Scenario: Borrower exceeds borrowing limit
    Given a borrower with borrowing limit 10000000
    When they apply for a loan of 15000000
    Then the application should be rejected with "BorrowingLimitExceededException"

  Scenario: Borrower has too many active loans
    Given a borrower with 2 active loans
    When they apply for a new loan
    Then the application should be rejected with "ActiveLoanLimitException"