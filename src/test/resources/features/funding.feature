Feature: Loan Funding

  Scenario: Lender berhasil mendanai sebagian pinjaman
    Given terdapat pinjaman dengan target dana Rp 10000000
    And lender memiliki saldo Rp 5000000
    When lender mendanai pinjaman sebesar Rp 5000000
    Then total dana terkumpul menjadi Rp 5000000
    And status pinjaman tetap FUNDING

  Scenario: Funding penuh memicu notifikasi observer
    Given terdapat pinjaman dengan target dana Rp 10000000
    And total dana yang sudah terkumpul Rp 9000000
    When lender mendanai pinjaman sebesar Rp 1000000
    Then total dana terkumpul menjadi Rp 10000000
    And semua observer mendapat notifikasi funding complete

  Scenario: Funding melebihi target ditolak
    Given terdapat pinjaman dengan target dana Rp 10000000
    And total dana yang sudah terkumpul Rp 9000000
    When lender mendanai pinjaman sebesar Rp 2000000
    Then sistem menolak dengan error "Total funding melebihi target pinjaman"

  Scenario: Pinjaman dibatalkan saat funding 50 persen
    Given terdapat pinjaman dengan target dana Rp 10000000
    And total dana yang sudah terkumpul Rp 5000000
    And terdapat 2 lender yang sudah berkontribusi
    When borrower membatalkan pinjaman
    Then status pinjaman menjadi CANCELLED
    And semua lender mendapat refund proporsional