package com.compose.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.fawry.fawryb2b.core.design_system.theme.LocalSizes
import com.fawry.fawryb2b.core.design_system.theme.Sizes

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun OFTheme(
    direction: LayoutDirection = LayoutDirection.Ltr,
    largeSpaces: LargeSpaces = LargeSpaces(),
    smallSpaces: SmallSpaces = SmallSpaces(),
    sizes: Sizes = Sizes(),
    cornerRadius: CornerRadius = CornerRadius(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides direction,
        LocalLargeSpaces provides largeSpaces,
        LocalSmallSpaces provides smallSpaces,
        LocalSizes provides sizes,
        LocalCornerRadius provides cornerRadius
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}
