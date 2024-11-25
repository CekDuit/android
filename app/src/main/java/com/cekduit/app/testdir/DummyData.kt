package com.cekduit.app.testdir

data class DummyData(val valueX: Float, val valueY: Float)

class TransactionDummy {
    val dummyData = TransactionList(
        date = "2021-08-01",
        totalExpense = 100000.0,
        totalIncome = 200000.0,
        transactions = listOf(
            Transaction(
                amount = 10000.0,
                description = "Makanan",
                type = TransactionType.INCOME,
                createdAt = System.currentTimeMillis(),
                timestamp = System.currentTimeMillis(),
                transactionId = "1"
            ),
            Transaction(
                amount = 10000.0,
                description = "Makanan",
                type = TransactionType.INCOME,
                createdAt = System.currentTimeMillis(),
                timestamp = System.currentTimeMillis(),
                transactionId = "1"
            ),
            Transaction(
                amount = 10000.0,
                description = "Makanan",
                type = TransactionType.INCOME,
                createdAt = System.currentTimeMillis(),
                timestamp = System.currentTimeMillis(),
                transactionId = "1"
            ),
            Transaction(
                amount = 10000.0,
                description = "Makanan",
                type = TransactionType.INCOME,
                createdAt = System.currentTimeMillis(),
                timestamp = System.currentTimeMillis(),
                transactionId = "1"
            ),
            Transaction(
                amount = 10000.0,
                description = "Makanan",
                type = TransactionType.INCOME,
                createdAt = System.currentTimeMillis(),
                timestamp = System.currentTimeMillis(),
                transactionId = "1"
            ),
        )
    )
}