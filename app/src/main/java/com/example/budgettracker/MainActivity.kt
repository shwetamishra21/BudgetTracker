package com.example.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgettracker.data.TransactionDatabase
import com.example.budgettracker.data.TransactionRepository
import com.example.budgettracker.screens.AddTransactionScreen
import com.example.budgettracker.screens.HomeScreen
import com.example.budgettracker.screens.SummaryScreen
import com.example.budgettracker.ui.theme.BudgetTrackerTheme
import com.example.budgettracker.viewmodel.SummaryViewModel
import com.example.budgettracker.viewmodel.TransactionViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TransactionDatabase.getDatabase(applicationContext)
        val repo = TransactionRepository(db.transactionDao())

        val transactionVM = ViewModelProvider(
            this,
            TransactionViewModel.Factory(repo)
        )[TransactionViewModel::class.java]

        val summaryVM = ViewModelProvider(
            this,
            SummaryViewModel.Factory(repo)
        )[SummaryViewModel::class.java]

        setContent {
            BudgetTrackerTheme {
                val navController = rememberNavController()
                val transactions by transactionVM.transactions.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            transactions = transactions,
                            onAddClick = { navController.navigate("add") },
                            onSummaryClick = { navController.navigate("summary") }
                        )
                    }

                    composable("add") {
                        AddTransactionScreen(
                            onSave = { title, amount, type ->
                                transactionVM.addTransaction(title, amount, type)
                                navController.popBackStack()
                            },
                            onCancel = { navController.popBackStack() }
                        )
                    }

                    composable("summary") {
                        SummaryScreen(viewModel = summaryVM)
                    }
                }
            }
        }
    }
}
