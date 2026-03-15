package com.example.etymo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EtymoColorScheme = darkColorScheme(
    primary = EtymoYellow,
    onPrimary = EtymoDark,
    primaryContainer = EtymoYellowDeep,
    onPrimaryContainer = EtymoDark,
    secondary = EtymoYellowGold,
    onSecondary = EtymoDark,
    secondaryContainer = EtymoDarkCard,
    onSecondaryContainer = EtymoYellow,
    tertiary = EtymoAmber,
    onTertiary = EtymoDark,
    background = EtymoDark,
    onBackground = EtymoWhite,
    surface = EtymoDarkSurface,
    onSurface = EtymoWhite,
    surfaceVariant = EtymoDarkCard,
    onSurfaceVariant = EtymoOffWhite,
    outline = GlassBorder,
)

@Composable
fun ETYMOTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EtymoColorScheme,
        typography = Typography,
        content = content
    )
}