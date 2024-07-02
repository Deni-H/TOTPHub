package com.denihilhamsyah.totphub.totp.presentation.component.time_indicator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.denihilhamsyah.totphub.R

@Composable
fun Dp.toFloat(): Float {
    return with(LocalDensity.current) {
        this@toFloat.toPx()
    }
}

@Composable
fun TimeIndicator(
    value: Int = 0,
    maxValue: Int = TimeIndicatorDefaults.MAX_VALUE,
    size: Dp = TimeIndicatorDefaults.minSize,
    animationSpec: Int = TimeIndicatorDefaults.ANIMATION_SPEC,
    backgroundIndicatorColor: Color = TimeIndicatorDefaults.backgroundIndicatorColor,
    foregroundIndicatorColor: Color = TimeIndicatorDefaults.foregroundIndicatorColor,
    backgroundIndicatorStrokeWidth: Dp = TimeIndicatorDefaults.backgroundIndicatorStrokeWidth,
    foregroundIndicatorStrokeWidth: Dp = TimeIndicatorDefaults.foregroundIndicatorStrokeWidth,
) {
    val foregroundStrokeWidth = foregroundIndicatorStrokeWidth.toFloat()
    val backgroundStrokeWidth = backgroundIndicatorStrokeWidth.toFloat()

    var allowedIndicatorValue by remember {
        mutableIntStateOf(maxValue)
    }

    allowedIndicatorValue =
        if (value <= maxValue) value else maxValue

    var animatedIndicatorValue by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage =
        (animatedIndicatorValue / maxValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (3.6 * percentage).toFloat(),
        animationSpec = tween(animationSpec),
        label = stringResource(R.string.indicator_animation),
    )

    Box(
        modifier = Modifier
            .size(size)
            .drawBehind {
                val componentSize = this.size / 1.25f

                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundStrokeWidth,
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundStrokeWidth,
                )
            },
    )
}

@Preview(showBackground = true)
@Composable
private fun TimeIndicatorPreview() {
    TimeIndicator(
        value = 100,
        maxValue = 100
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}