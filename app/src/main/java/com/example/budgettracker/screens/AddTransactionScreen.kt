package com.example.budgettracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracker.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onSave: (title: String, amount: Double, type: String) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Expense") }
    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(RoyalBlue, NavyBlue, SteelBlue)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Add Transaction",
                style = MaterialTheme.typography.headlineMedium,
                color = SoftWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Track your income and expenses",
                color = LightBlue,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(32.dp))

            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                label = { Text("Transaction Title") },
                leadingIcon = {
                    Icon(Icons.Default.Title, contentDescription = null, tint = TealAccent)
                },
                isError = titleError,
                supportingText = {
                    if (titleError) Text("Title cannot be empty", color = RedAccent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = SoftWhite,
                    unfocusedTextColor = SoftWhite,
                    focusedBorderColor = TealAccent,
                    unfocusedBorderColor = LightBlue.copy(alpha = 0.5f),
                    focusedLabelColor = TealAccent,
                    unfocusedLabelColor = LightBlue,
                    cursorColor = TealAccent
                )
            )

            Spacer(Modifier.height(16.dp))

            // Amount Input
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    amountError = false
                },
                label = { Text("Amount (₹)") },
                leadingIcon = {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = TealAccent)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = amountError,
                supportingText = {
                    if (amountError) Text("Enter valid amount", color = RedAccent)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = SoftWhite,
                    unfocusedTextColor = SoftWhite,
                    focusedBorderColor = TealAccent,
                    unfocusedBorderColor = LightBlue.copy(alpha = 0.5f),
                    focusedLabelColor = TealAccent,
                    unfocusedLabelColor = LightBlue,
                    cursorColor = TealAccent
                )
            )

            Spacer(Modifier.height(24.dp))

            // Type Selection
            Text(
                "Transaction Type",
                color = LightBlue,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Income Button
                Button(
                    onClick = { type = "Income" },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "Income") TealAccent else NavyBlue,
                        contentColor = SoftWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = if (type == "Income") 8.dp else 2.dp
                    )
                ) {
                    Text(
                        "Income",
                        fontWeight = if (type == "Income") FontWeight.Bold else FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }

                // Expense Button
                Button(
                    onClick = { type = "Expense" },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "Expense") RedAccent else NavyBlue,
                        contentColor = SoftWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = if (type == "Expense") 8.dp else 2.dp
                    )
                ) {
                    Text(
                        "Expense",
                        fontWeight = if (type == "Expense") FontWeight.Bold else FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SoftWhite
                    )
                ) {
                    Text("Cancel", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull()
                        when {
                            title.isBlank() -> titleError = true
                            amt == null || amt <= 0.0 -> amountError = true
                            else -> onSave(title.trim(), amt, type)
                        }
                    },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TealAccent,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
