package com.compose.app.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Data class to define small spacing values used in the UI
data class SmallSpaces(
    // Extra small space, typically used for very tight layouts or minimal spacing
    val extraSmall: Dp = 0.dp,

    // Normal small space, used for small separations between elements
    val normalSmall: Dp = 4.dp,

    // Small space, commonly used for padding or margins in standard components
    val small: Dp = 6.dp,

    // Medium space, often used for moderate separation between larger components
    val medium: Dp = 16.dp,

    // Medium space, often used for moderate separation between larger components
    val extraMedium: Dp = 24.dp,

    // Large space, suitable for significant separation in complex layouts
    val large: Dp = 32.dp,

    // Extra large space, useful for larger gaps or around major components
    val extraLarge: Dp = 48.dp,

    // Double extra large space, typically used for very large separations or padding
    val doubleExtraLarge: Dp = 64.dp,
)

// Data class to define larger spacing values for more extensive layouts
data class LargeSpaces(
    // Small large space, used for considerable padding or separation
    val small: Dp = 80.dp,

    // Medium large space, often used for substantial gaps in layouts
    val medium: Dp = 104.dp,

    // Large space, typically used for very large separations in complex UI
    val large: Dp = 240.dp,
)

// Composition locals to provide small and large spacing values throughout the UI

val LocalSmallSpaces = staticCompositionLocalOf { SmallSpaces() }
val LocalLargeSpaces = staticCompositionLocalOf { LargeSpaces() }

@Composable
fun Modifier.applyScreenSpaces() = padding(LocalSmallSpaces.current.medium)
