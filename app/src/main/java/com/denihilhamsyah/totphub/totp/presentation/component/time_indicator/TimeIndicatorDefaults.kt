package com.denihilhamsyah.totphub.totp.presentation.component.time_indicator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object TimeIndicatorDefaults {

    const val MAX_VALUE: Int = 100

    const val ANIMATION_SPEC: Int = 0

    val minSize: Dp = 40.dp

    val backgroundIndicatorStrokeWidth: Dp = 8.dp

    val foregroundIndicatorStrokeWidth: Dp = 8.dp

    val backgroundIndicatorColor: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val foregroundIndicatorColor: Color @Composable get() = MaterialTheme.colorScheme.primary
}