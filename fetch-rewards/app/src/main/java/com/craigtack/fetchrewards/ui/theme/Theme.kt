package com.craigtack.fetchrewards.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(content: @Composable () -> Unit) =
    MaterialTheme(
        colorScheme = lightColorScheme(
            surface = Color(0xFF6650a4),
            onSurface = Color.White,
        ),
        content = content
    )
