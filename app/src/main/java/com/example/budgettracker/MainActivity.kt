package com.example.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracker.ui.theme.BudgetTrackerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BudgetTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0D1B2A)
                ) {
                    AppHost(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHost(viewModel: TransactionViewModel) {
    var showingAdd by remember { mutableStateOf(false) }
    val transactions by viewModel.transactions.collectAsState()

    if (showingAdd) {
        AddTransactionScreen(
            onSave = { title, amount, type ->
                viewModel.addTransaction(title, amount, type)
                showingAdd = false
            },
            onCancel = { showingAdd = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFF0D1B2A))
        ) {
            Text(
                "Budget Dashboard",
                color = Color(0xFFE0E1DD),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
            val totalExpense = transactions.filter { it.type == "Expense" }.sumOf { it.amount }
            val balance = totalIncome - totalExpense

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Balance", color = Color(0xFF778DA9))
                    Text(
                        "₹%.2f".format(balance),
                        color = Color(0xFFE0E1DD),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    title = "Income",
                    amount = totalIncome,
                    color = Color(0xFF00B4D8),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Expense",
                    amount = totalExpense,
                    color = Color(0xFFE63946),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showingAdd = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B4D8))
            ) {
                Text("Add Transaction", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Recent Transactions", color = Color(0xFFE0E1DD))
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(transactions) { tx ->
                    TransactionRow(tx)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(tx: Transaction) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(tx.title, color = Color(0xFFE0E1DD))
                Text(tx.type, color = Color(0xFF778DA9))
            }
            Text(
                (if (tx.type == "Expense") "-" else "+") + "₹%.2f".format(tx.amount),
                color = if (tx.type == "Expense") Color(0xFFFF6B6B) else Color(0xFF00B4D8),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatCard(title: String, amount: Double, color: Color, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color(0xFF778DA9))
            Text(
                "₹%.2f".format(amount),
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
