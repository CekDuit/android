package com.cekduit.app.testdir

data class Transaction(
    val transactionId: String,
    val amount: Double,
    val timestamp: Long,
    val description: String,
    val type: TransactionType,
    val createdAt: Long
)

data class TransactionList(
    val transactions: List<Transaction>,
    val date: String,
    val totalIncome: Double,
    val totalExpense: Double
)

enum class TransactionType(val type: String) {
    INCOME("INCOME"),
    EXPENSE("EXPENSE")
}
