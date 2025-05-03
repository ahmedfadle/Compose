package com.fawry.fawryb2b.core.design_system.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Data class to define various size values in pixels
data class Sizes(
    // Small size for 24px, suitable for small elements or icons
    val extraSmall: Dp = 20.dp,

    // Small size for 24px, suitable for small elements or icons
    val small: Dp = 24.dp,

    // Medium size for 32px, commonly used for buttons or moderate elements
    val medium: Dp = 32.dp,

    // Large size for 40px, often used for larger buttons or medium elements
    val large: Dp = 40.dp,

    // Extra large size for 48px, typically used for prominent elements or cards
    val extraLarge: Dp = 48.dp,

    // Heading size for 56px, useful for larger headers or significant components
    val heading: Dp = 56.dp,

    // Substantial size for 64px, generally used for major UI elements
    val substantial: Dp = 64.dp,

    // Major size for 80px, ideal for large elements or icons
    val major: Dp = 80.dp,

    // Banner size for 160px, suitable for large banners or sections
    val banner: Dp = 160.dp,

    // Extensive size for 240px, often used for large images or components
    val extensive: Dp = 240.dp,
)

// Composition local to provide size values throughout the UI
val LocalSizes = staticCompositionLocalOf { Sizes() }