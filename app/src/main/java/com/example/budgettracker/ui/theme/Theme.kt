package com.example.budgettracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = TealAccent,
    onPrimary = SoftWhite,
    background = RoyalBlue,
    surface = NavyBlue,
    onBackground = SoftWhite,
    onSurface = SoftWhite,
    error = RedAccent,
    tertiary = LightBlue
)

private val LightColors = lightColorScheme(
    primary = TealAccent,
    onPrimary = Color.Black,
    background = SoftWhite,
    surface = SoftWhite,
    onBackground = RoyalBlue,
    onSurface = RoyalBlue,
    error = RedAccent,
    tertiary = LightBlue
)

@Composable
fun BudgetTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
