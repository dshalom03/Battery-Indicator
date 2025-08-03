package com.example.batteryindicator.batteryindicator

import androidx.annotation.IntRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.batteryindicator.R
import com.example.batteryindicator.ui.theme.BatteryIndicatorTheme
import org.jetbrains.annotations.Range
import org.koin.compose.koinInject

@Composable
fun BatteryIndicator(modifier: Modifier = Modifier) {

    val batteryIndicatorViewModel = koinInject<BatteryIndicatorViewModel>()
    val progress by batteryIndicatorViewModel.state.collectAsStateWithLifecycle()

    val heartAnimatedScale by animateFloatAsState(
        targetValue = if (progress <= 20) 1.05f else if (progress >= 80) 0.95f else 1f,
        label = "scale"
    )

    val heartColor by animateColorAsState(
        targetValue = if (progress <= 20f) MaterialTheme.colorScheme.onError else Color.LightGray
    )

    val cloverAnimatedScale by animateFloatAsState(
        targetValue = if (progress >= 80) 1.05f else if (progress <= 20) 0.95f else 1f,
        label = "scale"
    )

    val cloverColor by animateColorAsState(
        targetValue = if (progress <= 20f) Color.LightGray else MaterialTheme.colorScheme.primary
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.heart),
            tint = heartColor,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .graphicsLayer(
                    scaleX = heartAnimatedScale,
                    scaleY = heartAnimatedScale
                )
        )

        Indicator(modifier = Modifier.weight(1f), progress.toInt())

        Icon(
            painter = painterResource(R.drawable.clover),
            contentDescription = null,
            tint = cloverColor,
            modifier = Modifier
                .padding(start = 8.dp)
                .graphicsLayer(
                    scaleX = cloverAnimatedScale,
                    scaleY = cloverAnimatedScale
                )

        )
    }
}

private fun cellColor(progress: Int): Color = if (progress <= 20) {
    Color.Red
} else if (progress <= 50) {
    Color.Yellow
} else {
    Color.Green
}

@Composable
fun Indicator(modifier: Modifier = Modifier, progress: Int) {

    val cellColor = cellColor(progress)

    Box(modifier = modifier.fillMaxHeight()) {

        val density = LocalDensity.current.density
        val cornerRadius = CornerRadius(5.dp.dpToPx(density), 10f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(0f, 0f),
                            size = Size(width, height),
                        ),
                        bottomLeft = cornerRadius,
                        bottomRight = cornerRadius,
                        topLeft = cornerRadius,
                        topRight = cornerRadius
                    )
                )
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(width, height * 0.33f),
                            size = Size(width * 0.018f, height / 3f),
                        ),
                        bottomRight = cornerRadius,
                        topRight = cornerRadius
                    )
                )
            }
            drawPath(path, color = Color.LightGray)

            val cellHeight = height * 0.9f
            var xOffset = 2.dp.dpToPx(density)
            val yOffset = height * 0.05f

            val wholeCells = progress / 20
            val halfCells = (progress % 20) / 10

            (0 until wholeCells).forEach { i ->
                val cellWidth = (width / 5) - 4.dp.dpToPx(density)

                drawCell(
                    cellWidth = cellWidth,
                    cellHeight = cellHeight,
                    xOffset = xOffset,
                    yOffset = yOffset,
                    color = cellColor
                )
                xOffset += (width / 5)
            }

            (0 until halfCells).forEach { i ->
                val cellWidth = (width / 10) - 2.dp.dpToPx(density)

                drawCell(
                    cellWidth = cellWidth,
                    cellHeight = cellHeight,
                    xOffset = xOffset,
                    yOffset = yOffset,
                    color = cellColor
                )
                xOffset += (width / 10)
            }
        }

    }
}

fun DrawScope.drawCell(
    cellWidth: Float,
    cellHeight: Float,
    xOffset: Float,
    yOffset: Float,
    color: Color = Color.Red,
    cornerRadius: CornerRadius = CornerRadius(5.dp.dpToPx(density), 10f)
) {
    val cellPath = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(
                    offset = Offset(xOffset, yOffset),
                    size = Size(cellWidth, cellHeight),
                ),
                bottomLeft = cornerRadius,
                bottomRight = cornerRadius,
                topLeft = cornerRadius,
                topRight = cornerRadius
            )
        )
    }
    drawPath(cellPath, color = color)
}

internal fun Dp.dpToPx(density: Float): Float {
    return this.value * density
}


@Preview()
@Composable
fun BatteryIndicatorPreview(modifier: Modifier = Modifier) {

    BatteryIndicatorTheme {

        BatteryIndicator(modifier = Modifier.fillMaxWidth())
    }
}
