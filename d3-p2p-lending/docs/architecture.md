# Architecture Documentation
## P2P Lending Platform - Core Engine

---

## 1. Bounded Context
┌──────────────────────────────────────┐
│           USER CONTEXT               │
│   Borrower, Lender                   │
└──────────────────┬───────────────────┘
│
┌──────────────────▼───────────────────┐
│           LOAN CONTEXT               │
│   Loan, LoanState, RiskEngine        │
└──────────────────┬───────────────────┘
│ triggers
┌──────────────────▼───────────────────┐
│          FUNDING CONTEXT             │
│   Funding, FundingObserver           │
└──────────────────┬───────────────────┘
│ triggers
┌──────────────────▼───────────────────┐
│         REPAYMENT CONTEXT            │
│   Repayment, RepaymentStrategy       │
└──────────────────────────────────────┘

---

## 2. UML State Diagram — Loan Lifecycle
      ┌─────────┐
      │ PENDING │
      └────┬────┘
    ┌──────┴──────┐
    ▼             ▼
┌──────────┐   ┌──────────┐
│ APPROVED │   │ REJECTED │
└────┬─────┘   └──────────┘
│
▼
┌─────────┐
│ FUNDING │──────────────┐
└────┬────┘              │
│                   ▼
│             ┌───────────┐
│             │ CANCELLED │
│             └───────────┘
▼
┌──────────┐
│DISBURSED │
└────┬─────┘
│
▼
┌───────────┐
│ REPAYMENT │
└─────┬─────┘
│
▼
┌────────┐
│ CLOSED │
└────────┘

**Transition Rules:**
| From | To | Condition |
|---|---|---|
| PENDING | APPROVED | Passes all risk checks |
| PENDING | REJECTED | Fails any risk check |
| APPROVED | FUNDING | Opened to lenders |
| FUNDING | DISBURSED | 100% funded |
| FUNDING | CANCELLED | Borrower cancels |
| DISBURSED | REPAYMENT | Funds disbursed to borrower |
| REPAYMENT | CLOSED | All installments paid |

---

## 3. UML Sequence Diagram — Funding Flow
Lender        FundingService      Loan          FundingObserver
│                 │               │                  │
│──fund()────────▶│               │                  │
│                 │──findById()──▶│                  │
│                 │◀──loan────────│                  │
│                 │──addFunding()▶│                  │
│                 │               │──isFullyFunded() │
│                 │               │──notifyObservers()▶│
│                 │               │                  │──onFundingComplete()
│                 │──save()       │                  │
│◀────────────────│               │                  │

---

## 4. UML Class Diagram
┌─────────────────────────────┐
│          Borrower           │
├─────────────────────────────┤
│ - id: String                │
│ - name: String              │
│ - email: String             │
│ - creditScore: int          │
│ - borrowingLimit: Money     │
│ - activeLoanCount: int      │
├─────────────────────────────┤
│ + validateEligibility()     │
│ + incrementActiveLoan()     │
│ + decrementActiveLoan()     │
└──────────────┬──────────────┘
│ Aggregation (Borrower dapat hidup tanpa Loan)
◇
│
┌──────────────▼──────────────┐        ┌─────────────────────────┐
│            Loan             │        │      <<interface>>       │
├─────────────────────────────┤        │        LoanState        │
│ - id: String                │        ├─────────────────────────┤
│ - borrower: Borrower        │        │ + approve(loan)         │
│ - targetAmount: Money       │        │ + reject(loan)          │
│ - tenorMonths: int          │        │ + startFunding(loan)    │
│ - scheme: LoanScheme        │- - - ▷ │ + cancel(loan)          │
│ - state: LoanState          │        │ + disburse(loan)        │
│ - totalFunded: Money        │        │ + startRepayment(loan)  │
│ - fundings: List<Funding>   │        │ + close(loan)           │
│ - observers: List<Observer> │        │ + getStatus()           │
├─────────────────────────────┤        └────────────┬────────────┘
│ + approve()                 │                     │ Realization
│ + reject()                  │              - - - -┤- - - -
│ + startFunding()            │              ▷      │      ▷
│ + addFunding(funding)       │       PendingState  │  ApprovedState
│ + isFullyFunded()           │       RejectedState │  FundingState
│ + addObserver(observer)     │       DisbursedState│  RepaymentState
└──────────────┬──────────────┘       ClosedState   │  CancelledState
│
◆ Composition (Funding tidak bisa hidup tanpa Loan)
│
┌──────────────▼──────────────┐        ┌─────────────────────────┐
│          Funding            │        │          Lender         │
├─────────────────────────────┤        ├─────────────────────────┤
│ - id: String                │        │ - id: String            │
│ - loanId: String            │◇───────│ - name: String          │
│ - lenderId: String          │        │ - email: String         │
│ - amount: Money             │        ├─────────────────────────┤
│ - fundedAt: LocalDateTime   │        │ + getId()               │
├─────────────────────────────┤        │ + getName()             │
│ + getId()                   │        └─────────────────────────┘
│ + getLoanId()               │        Aggregation (Lender dapat
│ + getLenderId()             │        hidup tanpa Funding)
│ + getAmount()               │
│ + getFundedAt()             │
└─────────────────────────────┘
┌─────────────────────────────┐        ┌──────────────────────────────────────┐
│         Repayment           │        │          <<interface>>               │
├─────────────────────────────┤        │    RepaymentCalculationStrategy      │
│ - id: String                │        ├──────────────────────────────────────┤
│ - loanId: String            │- - - ▷ │ + calculate(loanId, principal, tenor)│
│ - dueDate: LocalDate        │        └──────────────────┬───────────────────┘
│ - amount: Money             │                           │ Realization
│ - status: RepaymentStatus   │                    - - - -┤- - - -
│ - penalty: Money            │                    ▷      │      ▷
├─────────────────────────────┤          FixedRateStrategy│  FloatingRateStrategy
│ + markAsPaid()              │                           ▷
│ + calculatePenalty()        │                    MurabahahStrategy
└─────────────────────────────┘
┌─────────────────────────────┐
│        <<interface>>        │
│       FundingObserver       │
├─────────────────────────────┤
│ + onFundingComplete(loan)   │
└──────────────┬──────────────┘
│ Realization
- - - -┤- - - -
      ▷      │      ▷
      LenderNotif.   │  DisbursementObserver
      Observer       ▷
      BorrowerNotifObserver
      ┌─────────────────────────────┐
      │      <<abstract>>           │
      │        RiskHandler          │
      ├─────────────────────────────┤
      │ # next: RiskHandler         │
      ├─────────────────────────────┤
      │ + setNext(handler)          │
      │ + handle(borrower, amount)  │
      └──────────────┬──────────────┘
      │ Inheritance
      ───────┤───────
      ▷      │      ▷
      CreditScore    │  BorrowingLimit
      Handler        ▷      Handler
      ActiveLoan
      Handler

**Relasi Legend:**
| Notasi | Nama | Keterangan |
|---|---|---|
| `◆───` | Composition | Tidak bisa hidup tanpa parent |
| `◇───` | Aggregation | Bisa hidup tanpa parent |
| `- - -▷` | Realization | Implementasi interface |
| `────▷` | Inheritance | Turunan class |
---

## 5. Design Patterns

### State Pattern
**Used in:** `Loan`, `LoanState`, all `*State` classes

**Why:** A loan has a strict lifecycle and cannot jump states
arbitrarily. State Pattern enforces valid transitions and throws
`InvalidStateTransitionException` for illegal ones, keeping
transition logic organized per state.

---

### Strategy Pattern
**Used in:** `RepaymentCalculationStrategy`, `FixedRateStrategy`,
`FloatingRateStrategy`, `MurabahahStrategy`

**Why:** Each loan scheme calculates repayment obligations
differently. Strategy Pattern allows the algorithm to be swapped
without modifying `Loan` or `RepaymentService`.

**Note:** Syariah (Murabahah) is modeled as a repayment calculation
scheme, not merely an interest algorithm, since it uses margin-based
contracts instead of interest rates. The team deliberately named the
interface `RepaymentCalculationStrategy` rather than `InterestStrategy`
to reflect this distinction.

---

### Observer Pattern
**Used in:** `FundingObserver`, `LenderNotificationObserver`,
`DisbursementObserver`, `BorrowerNotificationObserver`

**Why:** When funding reaches 100%, multiple parties must be
notified simultaneously. Observer Pattern decouples notification
logic from `Loan.addFunding()`, making it easy to add new
observers without modifying existing code.

---

### Factory Method Pattern
**Used in:** `RepaymentStrategyFactory`

**Why:** The correct `RepaymentCalculationStrategy` must be
instantiated based on `LoanScheme`. Factory Method centralizes
this creation logic, preventing scattered conditionals across
the codebase.

---

### Chain of Responsibility Pattern
**Used in:** `RiskHandler`, `CreditScoreHandler`,
`BorrowingLimitHandler`, `ActiveLoanHandler`

**Why:** Loan approval requires multiple sequential validations.
Chain of Responsibility allows each rule to be checked
independently and extended without modifying existing handlers.

---

## 6. Domain Model

### Entities
| Entity | Description |
|---|---|
| `Loan` | Aggregate root. Manages funding, state transitions, and observer notifications |
| `Borrower` | Loan applicant. Contains eligibility validation logic |
| `Lender` | Fund contributor |
| `Funding` | Lender's contribution to a specific loan |
| `Repayment` | Single installment in the repayment schedule |

### Value Objects
| Value Object | Description |
|---|---|
| `Money` | Immutable monetary amount with validation |
| `InterestRate` | Immutable rate value between 0.0 and 1.0 |
| `LoanScheme` | Enum: FIXED, FLOATING, MURABAHAH |
| `LoanStatus` | Enum: PENDING, APPROVED, REJECTED, FUNDING, DISBURSED, REPAYMENT, CLOSED, CANCELLED |
| `RepaymentStatus` | Enum: PENDING, PAID, LATE |

---

## 7. Key Design Decisions

### Rich Domain Model
Business logic such as funding validation, state transitions,
and observer notifications are placed inside domain entities,
not in service classes. This follows DDD principles and keeps
services thin.

### In-Memory Repository
All data is stored in `HashMap`-based repositories wrapped
behind interfaces. This allows domain and application layers
to remain unaware of the storage mechanism.

### Syariah as a Scheme
Syariah (Murabahah) operates on a fundamentally different
contract — margin-based, not interest-based. The team modeled
Strategy as a repayment calculation scheme to accurately
reflect this distinction.