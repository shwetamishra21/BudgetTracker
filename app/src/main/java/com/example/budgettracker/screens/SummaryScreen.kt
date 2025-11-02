package com.example.budgettracker.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracker.ui.theme.*
import com.example.budgettracker.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(viewModel: SummaryViewModel) {
    val state = viewModel.state.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                androidx.compose.ui.graphics.Brush.verticalGradient(
                    listOf(RoyalBlue, NavyBlue, SteelBlue)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Budget Summary",
                    color = SoftWhite,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // Donut Chart
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyBlue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DonutChart(
                            income = state.totalIncome,
                            expense = state.totalExpense,
                            saved = state.saved,
                            modifier = Modifier.size(220.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        // Legend
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            LegendItem("Income", TealAccent, state.totalIncome)
                            LegendItem("Expense", RedAccent, state.totalExpense)
                            LegendItem("Saved", GreenAccent, state.saved)
                        }
                    }
                }
            }

            item {
                // Summary Stats
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyBlue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        SummaryRow("Total Income", "₹%.2f".format(state.totalIncome), TealAccent)
                        Spacer(modifier = Modifier.height(12.dp))
                        SummaryRow("Total Expense", "₹%.2f".format(state.totalExpense), RedAccent)
                        Spacer(modifier = Modifier.height(12.dp))
                        SummaryRow("Amount Saved", "₹%.2f".format(state.saved), GreenAccent)
                        Spacer(modifier = Modifier.height(12.dp))
                        SummaryRow("Most Spent On", state.mostSpentCategory, AccentGold)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun DonutChart(
    income: Float,
    expense: Float,
    saved: Float,
    modifier: Modifier = Modifier
) {
    val total = income + expense + saved
    val incomeAngle = if (total > 0) (income / total) * 360f else 0f
    val expenseAngle = if (total > 0) (expense / total) * 360f else 0f
    val savedAngle = if (total > 0) (saved / total) * 360f else 0f

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier) {
            val strokeWidth = size.minDimension * 0.15f

            var startAngle = -90f

            // Draw Income Arc
            if (incomeAngle > 0f) {
                drawArc(
                    color = TealAccent,
                    startAngle = startAngle,
                    sweepAngle = incomeAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = Size(size.minDimension, size.minDimension),
                    topLeft = Offset((size.width - size.minDimension)/2f, (size.height - size.minDimension)/2f)
                )
            }
            startAngle += incomeAngle

            // Draw Expense Arc
            if (expenseAngle > 0f) {
                drawArc(
                    color = RedAccent,
                    startAngle = startAngle,
                    sweepAngle = expenseAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = Size(size.minDimension, size.minDimension),
                    topLeft = Offset((size.width - size.minDimension)/2f, (size.height - size.minDimension)/2f)
                )
            }
            startAngle += expenseAngle

            // Draw Saved Arc
            if (savedAngle > 0f) {
                drawArc(
                    color = GreenAccent,
                    startAngle = startAngle,
                    sweepAngle = savedAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = Size(size.minDimension, size.minDimension),
                    topLeft = Offset((size.width - size.minDimension)/2f, (size.height - size.minDimension)/2f)
                )
            }

            // Background circle (light shade)
            drawCircle(
                color = NavyBlue.copy(alpha = 0.45f),
                radius = size.minDimension / 2f,
                style = Stroke(width = strokeWidth)
            )
        }

        // Center text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Balance",
                color = LightBlue,
                fontSize = 14.sp
            )
            Text(
                "₹%.0f".format(income + saved - expense),
                color = SoftWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LegendItem(label: String, color: Color, value: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                label,
                color = LightBlue,
                fontSize = 11.sp
            )
            Text(
                "₹%.0f".format(value),
                color = SoftWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = LightBlue,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
