Feature: Loan Lifecycle Management
  Sebagai sistem P2P Lending
  Saya ingin memastikan setiap pinjaman melewati tahapan status yang benar
  Agar integritas data keuangan terjaga

  Scenario: Flow pinjaman dari pengajuan hingga pendanaan
    Given Pinjaman baru dibuat dengan status "PENDING"
    When Admin menyetujui pinjaman
    And Sistem membuka masa pendanaan
    Then Status pinjaman saat ini harus "FUNDING"

  Scenario: Pencegahan pencairan dana prematur
    Given Pinjaman dalam status "PENDING"
    When Sistem mencoba mencairkan dana
    Then Sistem harus menolak transisi dengan error "InvalidStateTransition"
