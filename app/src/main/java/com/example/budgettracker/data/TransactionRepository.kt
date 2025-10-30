package com.example.budgettracker

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val dao: TransactionDao) {
    fun getAllTransactions(): Flow<List<Transaction>> = dao.getAllTransactions()
    suspend fun insertTransaction(t: Transaction) = dao.insertTransaction(t)
    suspend fun deleteTransaction(id: Int) = dao.deleteTransaction(id)
}
