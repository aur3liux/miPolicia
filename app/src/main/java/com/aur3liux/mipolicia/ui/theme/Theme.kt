package com.aur3liux.mipolicia.ui.theme

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
    primary = Color(0xffffffff),
    secondary = Color(0xFF6D1A32),
    tertiary = Color(0xFF168075),
    background = Color(0xff6D1A32),
    surface = Color(0xFF009688),
    surfaceVariant = Color(0xFFBBA68B),
    inverseSurface = Color(0xFF7C273A),
    onSurface = Color(0xFF009688)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xff000000),
    secondary = Color(0xFFF3EFF0),
    tertiary = Color(0xFF235B4E),
    background = Color(0xffFFFFFF),
    surface = Color(0xFF6D1A32),
    surfaceVariant = Color(0xFF7C273A),
    inverseSurface = Color(0xFFBBA68B),
    onSurface = Color(0xFFEFEEF3)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun NaatsTheme(
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