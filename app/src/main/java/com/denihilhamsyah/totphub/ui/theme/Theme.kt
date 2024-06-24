package com.denihilhamsyah.totphub.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = blue,
    secondary = marine,
    background = darkBlue,
    tertiary = Pink80,
    surface = darkBlue,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = blue,
    secondary = Color.White,
    tertiary = cyanBlue,
    background = cyanBlue,
    surface = cyanBlue,
    onPrimary = Color.White,
    onSecondary = darkBlue,
    onTertiary = Color.White,
    onBackground = darkBlue,
    onSurface = darkBlue,
)

@Composable
fun TOTPHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}