package com.example.budgettracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        TransactionDatabase::class.java,
        "transaction_db"
    ).build()

    private val dao = db.transactionDao()
    private val repository = TransactionRepository(dao)

    val transactions = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTransaction(title: String, amount: Double, type: String) {
        viewModelScope.launch {
            repository.insertTransaction(Transaction(title = title, amount = amount, type = type))
        }
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
        }
    }
}
