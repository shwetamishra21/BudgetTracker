package com.example.budgettracker.data

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val dao: TransactionDao) {
    fun getAllTransactions(): Flow<List<Transaction>> = dao.getAll()

    suspend fun insertTransaction(transaction: Transaction) = dao.insert(transaction)

    suspend fun deleteTransaction(id: Int) = dao.deleteById(id)

    fun getTotalIncome(): Flow<Double?> = dao.getTotalIncome()

    fun getTotalExpense(): Flow<Double?> = dao.getTotalExpense()
}
