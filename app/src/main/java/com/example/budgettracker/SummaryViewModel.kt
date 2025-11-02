package com.example.budgettracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budgettracker.data.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SummaryState(
    val totalIncome: Float = 0f,
    val totalExpense: Float = 0f,
    val saved: Float = 0f,
    val mostSpentCategory: String = "None"
)

class SummaryViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _state = MutableStateFlow(SummaryState())
    val state: StateFlow<SummaryState> = _state

    init {
        viewModelScope.launch {
            repository.getAllTransactions().collect { list ->
                val income = list.filter { it.type == "Income" }.sumOf { it.amount }.toFloat()
                val expense = list.filter { it.type == "Expense" }.sumOf { it.amount }.toFloat()
                val saved = income - expense

                val mostSpentCategory = list
                    .filter { it.type == "Expense" }
                    .groupBy { it.title }
                    .maxByOrNull { entry -> entry.value.sumOf { it.amount } }
                    ?.key ?: "None"

                _state.value = SummaryState(
                    totalIncome = income,
                    totalExpense = expense,
                    saved = saved,
                    mostSpentCategory = mostSpentCategory
                )
            }
        }
    }

    class Factory(private val repository: TransactionRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
                return SummaryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
