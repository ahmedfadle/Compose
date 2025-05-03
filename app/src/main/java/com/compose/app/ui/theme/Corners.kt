package com.compose.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Data class to define corner radius sizes in pixels
data class CornerRadius(
    // Extra small radius, typically used for very subtle rounding
    val xSmall: Dp = 4.dp,
    // Extra small radius, typically used for very subtle rounding
    val extraSmall: Dp = 8.dp,

    // Small radius, commonly used for slight rounding on buttons and cards
    val small: Dp = 10.dp,

    // Medium radius, suitable for moderate rounding for various UI elements
    val medium: Dp = 12.dp,

    // Large radius, often used for more pronounced rounding on larger components
    val large: Dp = 14.dp,

    // Extra large radius, typically used for significant rounding on large elements
    val extraLarge: Dp = 16.dp,
)

// Composition local to provide corner radius values throughout the UI
val LocalCornerRadius = staticCompositionLocalOf { CornerRadius() }