package com.example.batteryindicator.batteryindicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batteryindicator.R
import com.example.batteryindicator.ui.theme.BatteryIndicatorTheme
import kotlin.concurrent.timer

@Composable
fun BatteryIndicator(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.heart),
            tint = MaterialTheme.colorScheme.onError,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )

        TabIndicatorScope(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.clover),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp)

        )


    }


}

@Composable
fun TabIndicatorScope(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxHeight()) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val cornerRadius = CornerRadius(10f, 10f)
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
            drawPath(path, color = Color.White)

        }


    }

}

@Preview()
@Composable
fun BatteryIndicatorPreview(modifier: Modifier = Modifier) {

    BatteryIndicatorTheme {

        BatteryIndicator(modifier = Modifier.fillMaxWidth())
    }
}
